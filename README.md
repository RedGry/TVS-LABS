# Работа №1. Поиск с помощью SOAP-сервиса

## Структура директории

Этот репозиторий содержит три основных модуля:

1. **client** - содержит Java-клиент для взаимодействия с сервисом.
2. **j2ee** - содержит Java EE приложение (использовалась jakarta), который разворачивается с помощью WildFly.
3. **standalone** - содержит standalone приложение развертываемое без помощи сервера приложений.

## Предварительные требования

1. JDK 17.
2. Установить Maven (последнюю версию).
3. Скачать и распаковать [Wildfly 32.0.0.Final](https://www.wildfly.org/downloads/#32.0.0.Final).
    + С помощью <wildfly-dir>/bin/add-user.bat создать пользователя для консоли управления Wildfly.
    + Скачайте PostgreSQL JDBC Driver (например, [postgresql-42.7.4.jar](https://jdbc.postgresql.org/download/)).
        + Поместить .jar драйвера в <wildfly-dir>/standalone/deployment.
    + Добавить datasource с именем java:/PostgresDS. Изменив файл <wildfly-dir>
      /standalone/configuration/standalone.xml добавив в раздел следующую запись:
      ```xml
      <datasource jndi-name="java:/PostgresDS" pool-name="PostgresDS">
          <connection-url>jdbc:postgresql://localhost:5432/postgres</connection-url>
          <driver-class>org.postgresql.Driver</driver-class>
          <driver>postgresql-42.7.4.jar</driver>
          <security user-name="postgres" password="postgres"/>
          <validation>
              <valid-connection-checker class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLValidConnectionChecker"/>
              <validate-on-match>true</validate-on-match>
              <exception-sorter class-name="org.jboss.jca.adapters.jdbc.extensions.postgres.PostgreSQLExceptionSorter"/>
          </validation>
      </datasource>
        ```
4. Убедитесь, что порт 8080 не занят.
5. Создать и заполнить бд:
   + Запустить docker-compose и создать бд + заполнить её: `docker-compose start db`.
   + Либо поднять свою бд, создать таблицу [create.sql](./utils/create.sql) и выполнить скрипт по заполнению [init.sql](./utils/init.sql)
6. Проверить, что в бд появились данные.
7. Экспортировать переменные из файла [.env](.env).

## Запуск и проверка standalone сервиса
1. `mvn clean install` внутри директории **lab1/standalone**.
2. `java -jar lab1-standalone.jar` внутри директории **lab1/standalone/target**.
3. `mvn clean install` внутри директории **lab1/client**.
4. `java -jar lab1-client.jar http://localhost:8080/PersonService?wsdl` внутри директории **lab1/client/target**.
5. Воспользоваться командами `help`, `exit`, `search`, `clear`.

## Запуск и проверка j2ee сервиса
1. `mvn clean install` внутри директории **lab1/j2ee**.
2. Задеплоить lab1-j2ee.war в WildFly.
   + Можно через интерфейс на дефолтном URL: http://localhost:9990.
   + Либо поместить файл в <wildfly-dir>/standalone/deployment.
3. `mvn clean install` внутри директории **lab1/client**.
4. `java -jar lab1-client.jar http://localhost:8080/lab-j2ee/PersonService?wsdl` внутри директории **lab1/client/target**.
5. Воспользоваться командами `help`, `exit`, `search`, `clear`.

## Пример запросов через клиента
+ `name=Olivia`
+ `age>30 AND age<35`
+ `(age>30 AND name=James) OR (name=Emily)`
+ `id=2 OR id=3 OR (name=Chris AND phoneNumber~%33%)`
