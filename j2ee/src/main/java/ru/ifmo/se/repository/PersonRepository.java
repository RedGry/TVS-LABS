package ru.ifmo.se.repository;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import ru.ifmo.se.model.entity.Person;

import java.util.List;
import java.util.Stack;

@ApplicationScoped
public class PersonRepository {
    @PersistenceContext(unitName = "PostgresDS")
    EntityManager entityManager;

    public List<Person> findPerson(String query, int limit, int offset) {
        CriteriaBuilder builder = entityManager.getCriteriaBuilder();
        CriteriaQuery<Person> criteriaQuery = builder.createQuery(Person.class);
        Root<Person> root = criteriaQuery.from(Person.class);

        Predicate predicate = parseQueryToPredicate(query, builder, root);
        if (predicate != null) {
            criteriaQuery.where(predicate);
        }

        return entityManager.createQuery(criteriaQuery)
                .setFirstResult(offset)
                .setMaxResults(limit)
                .getResultList();

    }

    private Predicate parseQueryToPredicate(String query, CriteriaBuilder builder, Root<Person> root) {
        if (query == null || query.trim().isEmpty()) {
            return null;
        }

        Stack<Predicate> predicateStack = new Stack<>();
        Stack<String> operatorStack = new Stack<>();

        int index = 0;
        while (index < query.length()) {
            char currentChar = query.charAt(index);

            if (currentChar == '(') {
                operatorStack.push(String.valueOf(currentChar));
                index++;
            } else if (currentChar == ')') {
                while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(")) {
                    String operator = operatorStack.pop();
                    Predicate right = predicateStack.pop();
                    Predicate left = predicateStack.pop();
                    predicateStack.push(combinePredicates(builder, left, right, operator));
                }
                operatorStack.pop();
                index++;
            } else if (Character.isWhitespace(currentChar)) {
                index++;
            } else {
                StringBuilder conditionBuilder = new StringBuilder();
                while (index < query.length() && query.charAt(index) != ' ' && query.charAt(index) != '(' && query.charAt(index) != ')') {
                    conditionBuilder.append(query.charAt(index));
                    index++;
                }
                String condition = conditionBuilder.toString().trim();

                if (condition.equalsIgnoreCase("AND") || condition.equalsIgnoreCase("OR")) {
                    while (!operatorStack.isEmpty() && !operatorStack.peek().equals("(") && precedence(operatorStack.peek()) >= precedence(condition)) {
                        String operator = operatorStack.pop();
                        Predicate right = predicateStack.pop();
                        Predicate left = predicateStack.pop();
                        predicateStack.push(combinePredicates(builder, left, right, operator));
                    }
                    operatorStack.push(condition);
                } else {
                    String[] parts = condition.split("=|!=|>=|<=|>|<|~|!~", 2);
                    if (parts.length != 2) {
                        throw new IllegalArgumentException("Invalid query format. Expected format: 'field operator value'");
                    }

                    String field = parts[0].trim();
                    String operator = condition.substring(parts[0].length(), condition.length() - parts[1].length()).trim();
                    String value = parts[1].trim().replace("\"", "");

                    Path<Object> path;
                    try {
                        path = root.get(field);
                    } catch (IllegalArgumentException e) {
                        throw new IllegalArgumentException("Could not resolve attribute '" + field + "' of '" + root.getJavaType().getName() + "'");
                    }

                    Predicate predicate = switch (operator) {
                        case "=" -> builder.equal(path, value);
                        case "!=" -> builder.notEqual(path, value);
                        case ">" -> builder.greaterThan(path.as(String.class), value);
                        case ">=" -> builder.greaterThanOrEqualTo(path.as(String.class), value);
                        case "<" -> builder.lessThan(path.as(String.class), value);
                        case "<=" -> builder.lessThanOrEqualTo(path.as(String.class), value);
                        case "~" -> builder.like(path.as(String.class), value);
                        case "!~" -> builder.notLike(path.as(String.class), value);
                        default -> throw new IllegalArgumentException("Unsupported operator: " + operator);
                    };
                    predicateStack.push(predicate);
                }
            }
        }

        while (!operatorStack.isEmpty()) {
            String operator = operatorStack.pop();
            Predicate right = predicateStack.pop();
            Predicate left = predicateStack.pop();
            predicateStack.push(combinePredicates(builder, left, right, operator));
        }

        return predicateStack.isEmpty() ? null : predicateStack.pop();
    }

    private Predicate combinePredicates(CriteriaBuilder builder, Predicate left, Predicate right, String operator) {
        if (operator.equalsIgnoreCase("AND")) {
            return builder.and(left, right);
        } else if (operator.equalsIgnoreCase("OR")) {
            return builder.or(left, right);
        } else {
            throw new IllegalArgumentException("Unsupported logical operator: " + operator);
        }
    }

    private int precedence(String operator) {
        if (operator.equalsIgnoreCase("AND")) {
            return 2;
        } else if (operator.equalsIgnoreCase("OR")) {
            return 1;
        } else {
            return 0;
        }
    }
}
