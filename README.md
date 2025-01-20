# Работа №7. Регистрация и поиск сервиса в реестре jUDDI
Требуется разработать приложение, осуществляющее регистрацию сервиса в реестре jUDDI, а также поиск сервиса в реестре и обращение к нему. Рекомендуется реализовать консольное приложение, которое обрабатывает две команды. Итог работы первой команды – регистрация сервиса в реестре; вторая команда должна осуществлять поиск сервиса, а также обращение к нему.

## Структура директории

Этот репозиторий содержит три основных модуля:

1. **client** - содержит Java-клиент для взаимодействия с сервисом.
2. **standalone** - содержит standalone приложение развертываемое без помощи сервера приложений.

## Предварительные требования

1. JDK 8 and JDK 17.
2. Установить Maven (последнюю версию).
3. Убедитесь, что порт 8080 / 8081 не заняты.
4. Создать и заполнить бд:
    + Запустить docker-compose и создать бд + заполнить её: `docker-compose start db`.
    + Либо поднять свою бд, создать таблицу [create.sql](./utils/create.sql) и выполнить скрипт по
      заполнению [init.sql](./utils/init.sql)
5. Проверить, что в бд появились данные.
6. Экспортировать переменные из файла [.env](.env).

## Запуск и проверка standalone сервиса
1. Скачать и запустить JUDDI: https://archive.apache.org/dist/juddi/juddi/3.3.9/
   + Важно: Чтобы не было русских символов и пробелов в пути. 
   + Главное поставить JRE_HOME и JAVA_HOME - правильно 8 версии и для клиента тоже придется использовать 8, т.к. на 11 и позже - не работает (там jakarta, а требует javax).
   + `set JAVA_OPTS=%JAVA_OPTS% -Djavax.xml.accessExternalDTD=all` -- добавь в файл catalina.(bat|sh), иначе не будет работать.
   + Запуск %JUDDI_DIR%/juddi-tomcat-3.3.9/bin/ - там startup.(bat|sh).
   + Пароль и логин: username="uddiadmin" password="da_password1" (%JUDDI_DIR%/juddi-tomcat-3.3.9/conf/tomcat-users)
1. `mvn clean install` внутри директории **standalone**.
2. `java -jar lab1-standalone.jar` внутри директории **standalone/target**.
3. `mvn clean install` внутри директории **client**.
4. `java -jar lab1-client.jar localhost 8080 uddiadmin da_password1` внутри директории **client/target**.
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

