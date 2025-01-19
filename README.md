# Работа №4-5-6. Сервис и Клиент переписать на REST
Необходимо выполнить задание из первой/второй/третьей работы, но с использованием REST-сервиса. Таблицу базы данных, а также код для работы с ней можно оставить без изменений.


## Структура директории

Этот репозиторий содержит два основных модуля:

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
4. `java -jar lab1-client.jar http://localhost:8080` внутри директории **client/target**.
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

