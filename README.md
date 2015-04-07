# HItemni

Hitemni  est une plateforme prévue d’abord pour des terminaux Androïd. Son rôle est la  mise en contact et la gestion des anciens élèves basée sur les réseaux  sociaux et la géo localisation. Elle sera disponible, par la suite, via un site  internet, ainsi que sur les principaux terminaux mobiles et tablettes sous forme  d'application web html 5/JavaScript.

## Overview

* [Features](#features)
* [Installation](#installation)

## Features

* Faire des recherches dans l’annuaire
* Se tenir informé(e) des prochains événements organisés par Hitema et s’y inscrire.
* Profiter de fonctionnalités sociales conviviales et pratiques
* Localiser les anciens de l’école qui peuvent travailler dans le même secteur voir la même entreprise.

## Installation

### Installation de Facebook sur l'emulateur

Pour installer l'APK:
1. Demarrer l'émulateur
2. Dézipper l'APK et taper:

* Mac
    `./adb install ~/Downloads/Facebook-version.apk`
* Windows
    `adb install %HOMEPATH%\Downloads\Facebook-version.apk`

3. Facebook appears in your emulator's home screen
4. Open Facebook and login using your regular Facebook username and password

### Ajouter Facebook SDK au projet

To use Facebook SDK in a project, add it as a build dependency and import it.

1. Go to Android Studio | New Project | Minimum SDK
2. Select "API 9: Android 2.3" or higher and create your new project.
3. After you create a new project, open
your_app > build.gradle
4. Add this to build.gradle before dependencies:
repositories { mavenCentral() }
5. Build your project. Now you can import com.facebook.FacebookSdk into your app.
You may need to add compile `com.facebook.android:facebook-android-sdk:4.0.0` to your build.gradle dependencies and rebuild.

### Ajouter le SDK à un projet existant

Follow Steps 3 - 5. in Add Facebook SDK to Your Project. 
After this you can import com.facebook.FacebookSdk.
