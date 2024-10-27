package ru.ifmo.se.repository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.criteria.*;
import ru.ifmo.se.model.entity.Person;
import ru.ifmo.se.soap.errors.FaultBean;
import ru.ifmo.se.soap.errors.PersonServiceException;

import java.util.List;
import java.util.Stack;

public class PersonRepository {
    private final EntityManagerFactory entityManagerFactory;

    public PersonRepository(EntityManagerFactory entityManagerFactory) {
        this.entityManagerFactory = entityManagerFactory;
    }

    public List<Person> findPerson(String query, int limit, int offset) throws PersonServiceException {
        if (limit < 0 || offset < 0) {
            throw new PersonServiceException(
                    "Invalid parameters",
                    new FaultBean("Limit and offset must be non-negative integers.")
            );
        }

        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
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
        } catch (Exception e) {
            throw new PersonServiceException(
                    "Error finding persons",
                    new FaultBean("An error occurred while processing the query. " + e.getMessage()),
                    e
            );
        }
    }

    public Person readPerson(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            return entityManager.find(Person.class, id);
        }
    }

    public int createPerson(Person person) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            entityManager.persist(person);
            entityManager.getTransaction().commit();
            return person.getId();
        }
    }

    public boolean updatePerson(int id, Person newDetails) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Person existingPerson = entityManager.find(Person.class, id);
            if (existingPerson == null) {
                return false;
            }
            existingPerson.setName(newDetails.getName());
            existingPerson.setSurname(newDetails.getSurname());
            existingPerson.setAge(newDetails.getAge());
            existingPerson.setAddress(newDetails.getAddress());
            existingPerson.setPhoneNumber(newDetails.getPhoneNumber());
            entityManager.getTransaction().commit();
            return true;
        }
    }

    public boolean deletePersonById(int id) {
        try (EntityManager entityManager = entityManagerFactory.createEntityManager()) {
            entityManager.getTransaction().begin();
            Person person = entityManager.find(Person.class, id);
            if (person == null) {
                return false;
            }
            entityManager.remove(person);
            entityManager.getTransaction().commit();
            return true;
        }
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
                        throw new IllegalArgumentException("Could not resolve attribute '" + field + "' of '" + root.getJavaType().getSimpleName() + "'");
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
