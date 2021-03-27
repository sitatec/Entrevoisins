# Entrevoisins
[![Build Status](https://travis-ci.com/sitatec/Entrevoisins.svg?branch=master)](https://travis-ci.com/sitatec/Entrevoisins) [![codecov](https://codecov.io/gh/sitatec/Entrevoisins/branch/master/graph/badge.svg?token=EP5SOZ08Y8)](https://codecov.io/gh/sitatec/Entrevoisins)

## Build
To build the project, first, you have to download the project zip or clone it `git clone https://github.com/sitatec/Entrevoisins`. The project is configured to be build using Gradle, you can use your favorite
android development environment to build and run the app or build it by manually running build tasks using the [gradle wrapper](https://docs.gradle.org/current/userguide/gradle_wrapper.html) : [gradlew](https://github.com/sitatec/Entrevoisins/blob/master/gradlew) for UNIX based os and [gradlew.bat](https://github.com/sitatec/Entrevoisins/blob/master/gradlew.bat) for windows :
1. In the command line:
    - `gradlew assembleDebug` for debug variant
    - `gradlew assembleRelease` for release variant
    - `gradlew assemble` to build all variants.
    - you can also run `gradlew tasks` to see all available gradle tasks.
2. In android studio:
    - you can select the build variant you want in the "Build Variants" tool window and then build the project.
    - you can also run a specific gradle task from the "Gradle" tool window.

## Folder Structure
All source code is inside the [app/src](https://github.com/sitatec/Entrevoisins/tree/master/app/src/) folder which is separated into three main directories:
1. #### [main](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main):
   The main folder contains all code and resources like images or XML files needed to build the project.
  The [main/java/com/openclassrooms/entrevoisins](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins) folder contains the app code and is structured as follow:
  - [di](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/di): contains the [dependacy injection](https://en.wikipedia.org/wiki/Dependency_injection) class (DI).
  - [events](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/events): contains app events (e.g: [`DeleteNeighbourEvent.java`](https://github.com/sitatec/Entrevoisins/blob/master/app/src/main/java/com/openclassrooms/entrevoisins/events/DeleteNeighbourEvent.java))  which will be handled throught the [EventBus](https://github.com/greenrobot/EventBus) library.
  - [model](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/model): as the name suggests, it contains the models of the app.
  - [service](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/service): contains services like api calls but actually the folder contains a fake api data generator.
  - [ui](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/ui): contains all classes that interact with ui such as Activities, Fragments, Adapters... and has two subfolders:
    - [neighbour_list](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/ui/neighbour_list): contains classes that are related to the [`ListNeighbourActivity.java`](https://github.com/sitatec/Entrevoisins/blob/master/app/src/main/java/com/openclassrooms/entrevoisins/ui/neighbour_list/ListNeighbourActivity.java) Activity (Home screen).
    - [neighbour_detail](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/ui/neighbour_list): contains classes that are related to the [`NeighbourDetailActivity.java`](https://github.com/sitatec/Entrevoisins/blob/master/app/src/main/java/com/openclassrooms/entrevoisins/ui/neighbour_detail/NeighbourDetailActivity.java) Activity (Neighbour "profile" screen).
  - [utils](https://github.com/sitatec/Entrevoisins/tree/master/app/src/main/java/com/openclassrooms/entrevoisins/utils): contains util classes/interfaces such as [`NeighbourListProvider.java`](https://github.com/sitatec/Entrevoisins/blob/master/app/src/main/java/com/openclassrooms/entrevoisins/utils/NeighbourListProvider.java) which is a `interface` that must be implemented by all neighbour data sources (api service, local data ...).
2. [test/java/com/openclassrooms/entrevoisins](https://github.com/sitatec/Entrevoisins/tree/master/app/src/test/java/com/openclassrooms/entrevoisins): contains unit tests. and
3. [androidTest/java/com/openclassrooms/entrevoisins](https://github.com/sitatec/Entrevoisins/tree/master/app/src/androidTest/java/com/openclassrooms/entrevoisins): contains instrumented tests.

## Tested on Pixel 4 (emulator) and Samsung A30 (Real device). 
