###Данное приложение дорабатывалось для поступления на стажировку от сайта JavaRash.

####Текст задания звучит так:

Задание на стажировку.

Нужно дописать приложение для администрирования сетевой ролевой игры, где администратор сможет редактировать
параметры персонажей(игроков), и раздавать банны. Должны быть реализованы следующие
возможности:

1. Получать список всех зарегистрированных игроков;
2. Создавать нового игрока;
3. Редактировать характеристики существующего игрока;
4. Удалять игрока;
5. Получать игрока по id;
6. Получать отфильтрованный список игроков в соответствии с переданными фильтрами;
7. Получать количество игроков, которые соответствуют фильтрам.


Для этого необходимо реализовать REST API в соответствии с документацией.

В проекте должна использоваться сущность Player, которая имеет поля:

| | |
---|---
|Long id | ID игрока|
String name | Имя персонажа (до 12 знаков включительно)
String title | Титул персонажа (до 30 знаков включительно)
Race race | Расса персонажа
Profession profession | Профессия персонажа
Integer experience | Опыт персонажа. Диапазон значений 0..10,000,000
Integer level | Уровень персонажа
Integer untilNextLevel | Остаток опыта до следующего уровня
Date birthday | Дата регистрации,Диапазон значений года 2000..3000 включительно
Boolean banned | Забанен / не забанен

Также должна присутствовать бизнес-логика:

Перед сохранением персонажа в базу данных (при добавлении нового или при обновлении характеристик
существующего), должны высчитываться:

- текущий уровень персонажа
- опыт необходимый для достижения следующего уровня
  
Текущий уровень и опыт для достижения следующего должны сохраняться в базе данных. 

Текущий уровень персонажа рассчитывается по формуле:

  L = (sqrt(2500 + 200*exp) - 50)/100

  где:

  - exp — опыт персонажа.

Опыт до следующего уровня рассчитывается по формуле:

N = 50*(L + 1)*(L + 2) - exp


где:

L — текущий уровень персонажа;
exp — опыт персонажа.

В приложении используй технологии:

1. Maven (для сборки проекта);
2. Tomcat 9 (для запуска своего приложения);
3. Spring;
4. Spring Data JPA;
5. MySQL (база данных (БД)).

###Моя работа.

Для реализации данного функционала я написал следующие классы:

- Player;
- PlayerRepository;
- PlayerServices;
- PlayerController;

Все остальные элементы приложения были реализованны в задании.

Когда задание проходило все вложенные тесты - оно считалось выполненным.

###Запуск.

Для запуска приложения необходимо:

- Открыть проект, используя файл pom.xml;
- Загрузить исходный код и документацию используемых фреймворковж
- Установить MySQL сервер. Логин и пароль root. Порт 3306. Залогиниться и
  выполнить скрипт init.sql, находящийся в корне проекта;
- В терминале перейти в папку с pom файлом и выполнить: "mvn -DskipTests=true clean install";
- Настроить запуск приложения через Tomcat (local), URL : http://localhost:8080/, HTTP port : 8080, JMX port: 1099, 
во вкладке Deployment нажать на «плюсик» и добавить артефакт «rpg:war exploded», jчистить поле Application context и нажать Ок;
- Запустить приложение.