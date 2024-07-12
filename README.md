FootyFaces Android App

FootyFaces is an Android application that provides users with a comprehensive view of football players and their details. The app is built using modern Android development practices and offers a seamless user experience for browsing and exploring player information.

Project Description

FootyFaces is developed in Kotlin using Jetpack Compose and follows the MVI (Model-View-Intent) architecture pattern. The app adheres to clean code principles and utilizes HILT for dependency injection. Kotlin Flow and Coroutines are employed for efficient concurrency and asynchronous programming.

Project Navigation and Working

The app consists of two main screens:

1) Players List Screen:

Displays a grid view of player images and names.
Data is fetched from the SportMonks API (https://api.sportmonks.com/v3/football/players).
Implements pagination to load more players as the user scrolls to the bottom of the screen.

2) Player Details Screen:

Accessed by tapping on a player in the grid view.
Shows detailed information about the selected player.
Users can return to the Players List Screen by tapping the back arrow in the app bar.

Features:

1) Grid view display of players with images and names.
2) Detailed player information on selection.
3) Pagination for smooth loading of additional players.
4) Adaptive UI optimized for mobile phones.
5) MVI architecture implementation with clean code practices.
6) HILT for dependency injection.
7) Kotlin Flow and Coroutines for asynchronous operations.
8) Unit tests for repository following TDD, use cases, and ViewModel.
9) UI automation tests for both screens.
10) Certificate pinning for enhanced network security.
11) ProGuard rules for code obfuscation.
12) Caching mechanism for API calls.
13) Accessibility features for an inclusive user experience.
14) Optimized code and custom components (e.g., CustomerRow and Text).

Assumptions:

The app requires an active internet connection to fetch player data.
The SportMonks API is assumed to be available and functioning correctly.

Future Improvements:

Implement offline mode with local data storage.
Add search functionality for finding specific players.
Implement analytics to track user engagement and app performance.





