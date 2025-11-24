# CampusGuide - Interactive Campus Navigation

![Kotlin](https://img.shields.io/badge/Kotlin-2.0.21-blue.svg?style=for-the-badge&logo=kotlin)
![Platform](https://img.shields.io/badge/Platform-Android-green.svg?style=for-the-badge&logo=android)
![UI](https://img.shields.io/badge/UI-Jetpack_Compose-blue.svg?style=for-the-badge&logo=jetpackcompose)

An Android application designed to help students, staff, and visitors navigate a university campus with ease. The app provides a list of key campus locations, allows users to add new locations, and offers real-time map tracking to guide them to their selected destination.

## Core Features

*   **Interactive Location List:** Displays a scrollable list of all important campus locations (e.g., "Library," "Admin Block," "Cafeteria").
*   **Real-Time Map Tracking:** Users can tap on any location to open a map view that shows their current position and guides them to the destination.
*   **Add Custom Locations:** Users can contribute to the campus map by adding new locations with their name, type, and geographic coordinates.
*   **Dynamic Theming:** Instantly switch between light and dark mode for comfortable viewing in any lighting condition.
*   **Modern, Clean UI:** Built entirely with Jetpack Compose and Material Design 3 for a fluid and intuitive user experience.

## Technical Implementation

This project is built using the latest Android development practices, emphasizing a clean, scalable, and maintainable architecture.

### 1. Architecture & Technologies

*   **Language:** **Kotlin** - The official, modern language for Android development, enabling concise and safe code.
*   **User Interface:** **Jetpack Compose** - A declarative UI toolkit used to build the entire native interface. This eliminates XML layouts and allows for a more dynamic and interactive UI.
*   **Design System:** **Material Design 3 (Material3)** - The latest design guidelines from Google, providing modern components, dynamic color, and consistent theming.
*   **Navigation:** **Jetpack Navigation for Compose** - Manages all screen transitions within the app, providing a robust and type-safe way to handle navigation logic.
*   **State Management:** The app follows a unidirectional data flow (UDF) pattern.
    *   **ViewModel (`androidx.lifecycle.ViewModel`):** Holds and manages UI-related data and state. It survives configuration changes like screen rotations.
    *   **StateFlow (`kotlinx.coroutines.flow.StateFlow`):** A state-holder observable flow used within the `ViewModel` to expose UI state to the Composables.
    *   **`collectAsState()`:** Collects values from the `StateFlow` and represents the latest value as Compose `State`, automatically triggering UI updates when the data changes.

### 2. Key Code Components & Libraries

*   **`LocationListScreen.kt`**: The main screen of the app. It uses a `Scaffold` to structure the UI with a `TopAppBar`, a `FloatingActionButton` for adding new locations, and a `LazyColumn` to efficiently display the list of locations.
*   **`LocationViewModel.kt`**: The brain behind the `LocationListScreen`. It is responsible for fetching the list of campus locations (likely from a local database or a remote server) and exposing it as a `StateFlow`.
*   **`Screen.kt`**: A sealed class that defines all navigation routes in the app, preventing typos and making navigation logic easy to manage.
*   **osmdroid (`org.osmdroid:osmdroid-android`)**: A powerful, open-source library used for displaying maps and handling map-based interactions, serving as an excellent alternative to Google Maps for many use cases.
*   **Google Play Services Location (`com.google.android.gms:play-services-location`)**: Used to get the device's current location for the real-time tracking feature on the map.

### 3. Navigation Graph

The app's navigation is structured around several key screens:

*   **`LocationList`** (Main Screen): Displays all locations.
*   **`MapTracking`**: The map screen that takes a location's name and coordinates as arguments to display the route.
*   **`AddLocation`**: A form for users to input details for a new campus location.
*   **`About`**: A simple screen providing information about the app.

## How to Build and Run

1.  **Clone the Repository:**
    
