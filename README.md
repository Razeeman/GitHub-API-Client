# GitHub API Client

Showcase app for Kotlin, MVVM, Retroft, GreenDAO, RxJava, Dagger.

Shows repository search results. Offline first with local cache in SQLite database.

## Technology stack
- Kotlin
- Androidx
- MVVM
- [Retrofit]
- [GreenDAO]
- [RxJava]
- [Dagger]

## Screenshots

[![Main Screen][screen1th]][screen1]

[screen1th]: dev_files/screens/screen1_thumbnail.png
[screen1]: dev_files/screens/screen1.png

[Retrofit]: https://github.com/Razeeman/GitHub-API-Client/tree/master/app/src/main/java/com/razeeman/showcase/githubapi/data/api
[GreenDAO]: https://github.com/Razeeman/GitHub-API-Client/tree/master/app/src/main/java/com/razeeman/showcase/githubapi/data/db
[RxJava]: https://github.com/Razeeman/GitHub-API-Client/blob/master/app/src/main/java/com/razeeman/showcase/githubapi/ui/search/SearchViewModel.kt
[Dagger]: https://github.com/Razeeman/GitHub-API-Client/tree/master/app/src/main/java/com/razeeman/showcase/githubapi/di

### Steps to integrate GreenDAO into Kotlin app

- Put greendao gradle plugin before kotlin plugin when applying plugins in app gradle file.

```
apply plugin: 'org.greenrobot.greendao'
apply plugin: 'kotlin-android'
```

- Put this sript into app gradle file, which allows greendao gradle task to run before kotlin debug and generate necessary classes.

```
tasks.whenTaskAdded { task ->
    if (task.name == 'kaptDebugKotlin') {
        task.dependsOn tasks.getByName('greendao')
    }
}
```

- GreenDAO entities should be written in java.
