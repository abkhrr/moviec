"# moviec" 

- App that clone netflix with TMDB API

## Libraries:

* ViewModel
* LiveData
* Data Binding
* Retrofit2 & OkHttp3
* Reactive Programming with Flow, Flowable and RXjava
* Material-Components
* App Compat
* Dagger Hilt
* Coroutines
* Modularization
* Navigation Component
* Etc.

## Features:

* Home Retrieve Movies ( Popular, Header, Etc)
* Playing movie trailers.
* Favorite or unfavorite module.
* Details movie.

## Content
* This project seeks to implement a good Clean Arch for good maintenance. This application also tries to follow good Clean Arch principles, namely the SOLID Principles, (read here https://en.wikipedia.org/wiki/SOLID) For more details about Android Clean Architecture.

* Hilt is a dependency injection library for Android that reduces the boilerplate of doing manual dependency injection in your project. Doing manual dependency injection requires you to construct every class and its dependencies by hand, and to use containers to reuse and manage dependencies. Hilt provides a standard way to use DI in your application by providing containers for every Android class in your project and managing their lifecycles automatically. Hilt is built on top of the popular DI library Dagger to benefit from the compile-time correctness, runtime performance, scalability, and Android Studio support that Dagger provides. For more information, see Hilt and Dagger. This guide explains the basic concepts of Hilt and its generated containers. It also includes a demonstration of how to bootstrap an existing app to use Hilt. read more about hilt at (https://developer.android.com/training/dependency-injection/hilt-android)

# Modularization:
* App layer module
* Data layer module
* Domain layer module
* Features layer module
* Utils layer module
* Navigation module

## API:
* TMDB API
