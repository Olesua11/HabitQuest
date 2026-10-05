# HabitQuest

HabitQuest is a gamified Android habit tracker built with Kotlin and Jetpack Compose.

## Features
- Create custom daily habits/quests
- Easy / Medium / Hard reward tiers
- Earn XP and coins for completing habits
- Automatic levels and ranks
- Daily completion state
- Streak calculation
- Achievement gallery
- Statistics dashboard
- Local Room database
- Dark RPG-inspired Material 3 interface
- Bottom navigation

## Stack
- Kotlin
- Jetpack Compose
- Material 3
- MVVM-style ViewModel state
- Room
- Kotlin Coroutines + Flow
- Navigation Compose

## Open in Android Studio
1. Extract the ZIP.
2. Open the `HabitQuest` folder in Android Studio.
3. Use JDK 17 for Gradle.
4. Let Android Studio finish Gradle Sync.
5. Run on an emulator or Android device with Android 8.0+ (API 26+).

## Build configuration
- AGP 8.9.3
- Gradle 8.11.1
- Kotlin 2.1.20
- compileSdk / targetSdk 35
- minSdk 26

The app seeds four demo habits on first launch so the interface is not empty.

### If Android Studio asks for a Gradle distribution
This archive intentionally contains only project sources/build scripts. Android Studio can configure Gradle when the project is opened. Select Gradle 8.11.1 and JDK 17 if prompted. After the first successful sync, Android Studio can generate/use the wrapper normally.
