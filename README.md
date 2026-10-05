# HabitQuest 

**HabitQuest** — Android-приложение для отслеживания привычек с элементами RPG.

Вместо обычного списка задач пользователь получает опыт, монеты, повышает уровень, поддерживает серии выполнения привычек и открывает достижения.

Главная идея проекта — превратить повседневные привычки в небольшие игровые квесты.

##  Возможности

- создание и управление привычками;
- выполнение привычек с начислением XP;
- получение монет;
- система уровней;
- streak — серии дней без пропусков;
- выбор сложности привычки;
- система достижений;
- профиль пользователя;
- статистика прогресса;
- локальное хранение данных;
- тёмный интерфейс в RPG-стилистике.

##  Скриншоты

<p align="center">
  <img src="2c7amd7_NJjwGbIQsKmsqwBxPWqIpWvnZTHIqVoM6IVzuecxKMYDSIRFYaTau0HuFjgC81urPCVe3fEMzvtYtfLL.jpg" width="180"/>
  <img src="dCt0FcB7IwqaqScCppreY8h7JNREiz6bU0et_dNws9iIOu5NjiFIEKdkaBJqfqYnDzUBezLhK36bASbTUEs2PhMA.jpg" width="180"/>
  <img src="KcmpcBPh65K7V2zqkieNYgN-3QwNWSVP2eHUApWAGv2OPYPh3BIFhjo3IkNyoycU2y02rpeEauxqK5uPnz-rOZ6EhPmcVg.jpg" width="180"/>
  <img src="N3-UhiA0Nh5oL7hgXxsZVbKyEHTTyUxT1Ig2ODUrlSwADQRIKT1d7vf42BhtmaloYj0z6KUIIQ9jmEdX4CZ8AYe5.jpg" width="180"/>
  <img src="qyFSe1c5A_K_98slet2O--5IYGxVQN6GwcqAvq9OaozIVkLqkeA0P5PdTtWrXPC1Ks5s3Hw6oRv8Lr_2wl_SBfyM.jpg" width="180"/>
</p>

##  Как работает приложение

Каждая привычка воспринимается как небольшой квест.

За выполнение пользователь получает:

- XP;
- монеты;
- прогресс серии;
- продвижение к достижениям.

Награда зависит от сложности привычки.

По мере накопления опыта пользователь повышает уровень.

##  Технологии

- Kotlin
- Jetpack Compose
- Material 3
- MVVM
- Room Database
- Kotlin Coroutines
- Flow
- Navigation Compose
- Gradle Kotlin DSL

## Архитектура

Проект построен по MVVM-подходу.

```text
data
├── database
├── dao
├── entities
└── repository

ui
├── home
├── achievements
├── statistics
└── profile

navigation
viewmodel
