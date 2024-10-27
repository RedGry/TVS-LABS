# Работа №2. Реализация CRUD с помощью SOAP-сервиса
В данной работе в веб-сервис, разработанный в первой работе, необходимо добавить методы для создания, изменения и удаления записей из таблицы.  
Метод создания должен принимать значения полей новой записи, метод изменения – идентификатор изменяемой записи, а также новые значения полей, а метод удаления – только идентификатор удаляемой записи.  
Метод создания должен возвращать идентификатор новой записи, а методы обновления или удаления – статус операции.  
В данной работе следует вносить изменения только в standalone-реализацию сервиса.   
В соответствии с изменениями сервиса необходимо обновить и клиентское приложение.  

## Структура директории

Этот репозиторий содержит три основных модуля:

1. **client** - содержит Java-клиент для взаимодействия с сервисом.
2. **standalone** - содержит standalone приложение развертываемое без помощи сервера приложений.

## Предварительные требования

1. JDK 17.
2. Установить Maven (последнюю версию).
3. Убедитесь, что порт 8080 не занят.
4. Создать и заполнить бд:
    + Запустить docker-compose и создать бд + заполнить её: `docker-compose start db`.
    + Либо поднять свою бд, создать таблицу [create.sql](./utils/create.sql) и выполнить скрипт по
      заполнению [init.sql](./utils/init.sql)
5. Проверить, что в бд появились данные.
6. Экспортировать переменные из файла [.env](.env).

## Запуск и проверка standalone сервиса

1. `mvn clean install` внутри директории **standalone**.
2. `java -jar lab1-standalone.jar` внутри директории **standalone/target**.
3. `mvn clean install` внутри директории **client**.
4. `java -jar lab1-client.jar http://localhost:8080/PersonService?wsdl` внутри директории **client/target**.
5. Воспользоваться командами `help`, `exit`, `search`, `findById`, `create`, `update`, `delete`, `clear`.

## Пример запросов через клиента

### `search` command:

Filter persons based on a query with optional limit and offset

+ `name=Olivia`
+ `age>30 AND age<35`
+ `(age>30 AND name=James) OR (name=Emily)`
+ `id=2 OR id=3 OR (name=Chris AND phoneNumber~%33%)`

### `findById` command

Find person by ID

### `create` command

Create a new person

### `update` command

Update an existing person by ID

### `delete` command

Delete a person by ID

