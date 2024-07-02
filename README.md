FootyFaces Android App
FootyFaces is an Android application that provides users with a comprehensive view of football players and their details. The app is built using modern Android development practices and offers a seamless user experience for browsing and exploring player information.

Project Description
FootyFaces is developed in Kotlin using Jetpack Compose and follows the MVI (Model-View-Intent) architecture pattern. The app adheres to clean code principles and utilizes HILT for dependency injection. Kotlin Flow and Coroutines are employed for efficient concurrency and asynchronous programming.

Project Navigation and Working

The app consists of two main screens:

Players List Screen:

Displays a grid view of player images and names.
Data is fetched from the SportMonks API (https://api.sportmonks.com/v3/football/players).
Implements pagination to load more players as the user scrolls to the bottom of the screen.

Player Details Screen:

Accessed by tapping on a player in the grid view.
Shows detailed information about the selected player.
Users can return to the Players List Screen by tapping the back arrow in the app bar.

Features:

Grid view display of players with images and names.
Detailed player information on selection.
Pagination for smooth loading of additional players.
Adaptive UI optimized for mobile phones.
MVI architecture implementation with clean code practices.
HILT for dependency injection.
Kotlin Flow and Coroutines for asynchronous operations.
Unit tests for repository following TDD, use cases, and ViewModel.
UI automation tests for both screens.
Certificate pinning for enhanced network security.
ProGuard rules for code obfuscation.
Caching mechanism for API calls.
Accessibility features for an inclusive user experience.
Optimized code and custom components (e.g., CustomerRow and Text).

Assumptions
The app requires an active internet connection to fetch player data.
The SportMonks API is assumed to be available and functioning correctly.

Future Improvements
Implement offline mode with local data storage.
Add search functionality for finding specific players.
Implement analytics to track user engagement and app performance.





