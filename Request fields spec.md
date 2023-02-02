# Спецификация для полей запросов на эндпоинты:

## 1) Контракт (Contract):
* ### Добавление (POST: "http:/localhost:8080/api/contracts/add"):
    * **id** - отсутствует 
    * **name** - от 3 до 30 символов
    * **contractType** - без ограничений
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **userId** - отсутствует
* ### Изменение (PUT: "http:/localhost:8080/api/contracts/update"):
    * **id** - обязательное поле, больше 1
    * **name** - от 3 до 30 символов
    * **contractType** - без ограничений
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **userId** - обязательное поле, больше 1
## 2) Контракт с контрагентом (Counterparty contract):
* ### Добавление (POST: "http:/localhost:8080/api/counterparty_contracts/add"):
    * **id** - отсутствует
    * **name** - от 3 до 30 символов
    * **contractType** - без ограничений
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **counterpartyId** - обязательное поле, больше 1
    * **contractId** - обязательное поле, больше 1
* ### Изменение (PUT: "http:/localhost:8080/api/counterparty_contracts/update"):
    * **id** - обязательное поле, больше 1
    * **name** - от 3 до 30 символов
    * **contractType** - без ограничений
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **counterpartyId** - обязательное поле, больше 1
    * **contractId** - обязательное поле, больше 1
## 3) Этап (Stage):
* ### Добавление (POST: "http:/localhost:8080/api/counterparty_contracts/add"):
    * **id** - отсутствует
    * **name** - от 3 до 30 символов
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **approxSalary** - больше 0.0
    * **approxCredit** - больше 0.0
    * **salary** - больше 0.0
    * **credit** - больше 0.0
    * **contractId** - обязательное поле, больше 1
* ### Изменение (PUT: "http:/localhost:8080/api/counterparty_contracts/update"):
    * **id** - обязательное поле, больше 1
    * **name** - от 3 до 30 символов
    * **approxBeginDate** - без ограничений
    * **approxEndDate** - без ограничений
    * **beginDate** - без ограничений
    * **endDate** - без ограничений
    * **sum** - больше 0.0
    * **approxSalary** - больше 0.0
    * **approxCredit** - больше 0.0
    * **salary** - больше 0.0
    * **credit** - больше 0.0
    * **contractId** - обязательное поле, больше 1
## 4) Организация-контрагент (Counterparty):
* ### Добавление (POST: "http:/localhost:8080/api/counterparties/add"):
    * **id** - отсутствует
    * **name** - от 3 до 30 символов
    * **address** - от 5 до 50 символов
    * **inn** - 10 цифр
* ### Изменение (PUT: "http:/localhost:8080/api/counterparties/update"):
    * **id** - обязательное поле, больше 1
    * **name** - от 3 до 30 символов
    * **address** - от 5 до 50 символов
    * **inn** - 10 цифр
## 4) Пользователь (User):
* ### Добавление (POST: "http:/localhost:8080/api/users/add"):
  * **id** - отсутствует
  * **FIO** - от 5 до 50 символов
  * **username** - от 3 до 50 символов
  * **password** - от 3 до 50 символов
  * **expirationDate** - отсутствует 
  * **role** - отсутствует
* ### Изменение (PUT: "http:/localhost:8080/api/users/update"):
  * **id** - отсутствует
  * **FIO** - от 5 до 50 символов
  * **username** - от 3 до 50 символов
  * **password** - от 3 до 50 символов
  * **expirationDate** - отсутствует
  * **role** - отсутствует