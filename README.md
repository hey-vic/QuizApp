# QuizApp
Викторина по вопросам из Open Trivia Database.

API: https://opentdb.com/api_config.php

<img src="https://github.com/hey-vic/QuizApp/blob/screenshots/quiz-app-gif.gif" width="288" height="640"/>

## Стек
- Kotlin (Coroutines, Flow)
- Jetpack Compose
- MVVM, Clean Architecture
- Retrofit
- Dagger-Hilt

## Скриншоты
![quiz-app-screenshots](https://github.com/hey-vic/QuizApp/assets/58303400/547f5fe9-2b63-410a-8bb6-163ad1fcb485)

## Функции
- Выпадающий список с возможностью ввода текста для выбора категории
- Сообщение об ошибке, если не выбрана категория/сложность, либо нет подключения к интернету
- Анимации на кнопках и сообщении о загрузке, анимированное изменение цвета фона
- Отображается прогресс (количество пройденных/оставшихся вопросов, оставшееся время)
- Если время вышло, автоматически отображается правильный ответ
- Подсчитывается количество правильных ответов
