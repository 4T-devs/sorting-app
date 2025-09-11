# Sorting App
Консольное Java-приложение для сортировки данных.

## Содержание
* [Требования](#требования)
* [Установка](#установка)
* [Сборка и запуск](#сборка-и-запуск)
* [Тесты](#тесты)
* [Проверка качества кода](#проверка-качества-кода)
* [CI/CD](#cicd)

## Требования

1. Java 21
2. Gradle 8+
3. Git

## Установка

Клонируем репозиторий по SSH:
```
git clone git@github.com:4T-devs/sorting-app.git
cd sorting-app
```

Проверяем версию Java:
```
java -version
```

Используем Gradle wrapper для установки зависимостей:
```
./gradlew build
```

## Сборка и запуск

Сборка проекта (без проверок):
```
./gradlew clean build
```

Сборка проекта (со всеми проверками):
```
./gradlew build
```

Запуск приложения:
```
./gradlew run
```

## Тесты

Запуск тестов JUnit 5:
```
./gradlew test
```

Генерация отчёта покрытия кода тестами:
```
./gradlew jacocoTestReport
```

Отчёт будет в:
```
build/reports/jacoco/test/html/index.html
```

## Проверка качества кода
### Checkstyle
Запуск
```
./gradlew checkstyleMain checkstyleTest
```

Отчёт:
```
build/reports/checkstyle/main.html
```
### PMD
Запуск:
```
./gradlew pmdMain pmdTest
```
Отчёт:
```
build/reports/pmd/main.html
```

### Spotless
Проверка форматирования:
```
./gradlew spotlessCheck
```

Автоформатирование:
```
./gradlew spotlessApply
```

## CI/CD

Используется GitHub Actions (.github/workflows/ci.yml):

Запускает сборку на каждом push или pull request в main

Проверяет код на форматирование, Checkstyle и PMD

Запускает тесты и проверку покрытия Jacoco (минимум 70% покрытия)

Загружает HTML-отчёты артефактами