## Инструкция по запуску:

### 1) Docker:
На целевой платформе должен быть установлен **Docker**:
* **Linux**: `sudo apt install snapd`
`sudo snap install docker`
* **MacOS**: `brew install docker`
* **Windows**: Процесс установки по ссылке - https://docs.docker.com/desktop/install/windows-install/
### 2) Запуск:
1) `git clone git@github.com:igor-shkurov/NIC_contract.git`
2) В корневой папке: `docker compose up`

Первый запуск будет относительно долгим, т.к. потребует загрузки всех зависимостей, этого не потребуется в дальнейшем, если вы не собираетесь менять содержание **pom.xml**.

После загрузки зависимостей, сборки jar-файла и инициализации всех 4-ёх контейнеров по адресу http://localhost:8081/auth будет доступен сайт.

При инициализации доступен админский профиль **admin:root**.


### **Готово!**



## Дополнительная информация:

* Frontend-сервер запускается из папки **src/client** командой `npm run serve`
* Backend-сервер запускается:
  * В среде разработки IntelliJ Idea (_предварительно нужно импортировать проект для Maven_)
  * Как _jar_ файл
* Используемые порты для **localhost**:
  * **8080** - Backend-сервер 
  * **8081** - Frontend-сервер 
  * **3307** - MySQL
  * **27018** - MongoDB

