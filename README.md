# BingeBox 🎬

A modern Android application for discovering, searching, and managing your personal movie library. Built with MVVM architecture and modern Android development practices.

## 📱 Features

### 🔍 Movie Discovery
- **Search Movies**: Find movies using an external movie database API
- **Grid Layout**: Browse search results in an intuitive 2-column grid
- **Movie Details**: Tap any movie to view detailed information in a custom dialog
- **Default Search**: App loads with a default "Harry Potter" search for immediate content

### 📚 Personal Library
- **Save Movies**: Add your favorite movies to a personal library
- **Local Storage**: All library data is stored locally using Room database
- **Library Search**: Search within your saved movies
- **Manage Collection**: Add, update, or remove movies from your library

### 🔄 Smart State Management
- **Search Persistence**: Last search query and results are preserved across app navigation
- **Smooth Navigation**: Seamless switching between Home and Library screens
- **Configuration Survival**: App state survives screen rotations and other configuration changes

### 📝 Movie Reviews
- **Review System**: Dedicated screen for movie reviews and ratings

## 🛠️ Tech Stack

### Architecture
- **MVVM (Model-View-ViewModel)** - Clean separation of concerns
- **Repository Pattern** - Single source of truth for data management
- **LiveData** - Reactive UI updates

### Android Components
- **Jetpack Components**: ViewModel, LiveData, Room, Fragments
- **Material Design**: Bottom Navigation, Progress Bars, Custom Dialogs
- **RecyclerView** with GridLayoutManager for efficient list display

### Networking & Data
- **Retrofit** - REST API client for movie data fetching
- **Room Persistence Library** - Local database for user's movie library
- **ExecutorService** - Background thread management for database operations

## 🚀 Getting Started

### Prerequisites
- Android Studio Arctic Fox or later
- Android SDK API 21+ (Android 5.0)
- Internet connection for movie data fetching

### Installation

1. **Clone the repository**
   ```bash
   git clone https://github.com/yourusername/BingeBox.git
   cd BingeBox
   ```

2. **Open in Android Studio**
   - Launch Android Studio
   - Select "Open an existing project"
   - Navigate to the cloned directory and open

3. **Configure API Keys**
   - Open `app/build.gradle`
   - Add your movie API credentials in the `buildConfigField`:
   ```gradle
   android {
       defaultConfig {
           buildConfigField "String", "API_KEY", '"your_api_key_here"'
           buildConfigField "String", "API_HOST", '"your_api_host_here"'
       }
   }
   ```

4. **Build and Run**
   - Sync project with Gradle files
   - Run the app on an emulator or physical device

## 🏗️ Project Architecture

```
app/src/main/java/com/example/bingebox/
├── adapters/           # RecyclerView adapters
├── api_service/        # API models and Retrofit service
├── database/           # Room database entities, DAOs
├── dialogBox/          # Custom dialog implementations
├── fragments/          # UI fragments (Home, Library)
├── repository/         # Data repository layer
├── viewmodel/          # ViewModels for UI logic
├── MainActivity.java   # Main activity with navigation
├── SplashActivity.java # App entry point
├── Review.java         # Review screen activity
└── SharedViewModel.java # Shared data between components
```

## 🔧 Key Components

### MainActivity
- Hosts the main navigation with BottomNavigationView
- Manages fragment transitions between Home and Library
- Handles search input and keyboard interactions

### HomeFragment
- Displays movie search results in a grid layout
- Implements search functionality with API integration
- Shows loading states and handles empty results

### LibraryFragment
- Displays user's saved movies from local database
- Provides search functionality within the library
- Manages CRUD operations for saved movies

### View_Model
- Bridges UI and Repository layer
- Manages LiveData for reactive UI updates
- Handles both API calls and database operations

### MovieRepository
- Single source of truth for movie data
- Manages API calls using Retrofit
- Handles local database operations using Room

## 📊 Data Flow

1. **Search Flow**: User Input → MainActivity → HomeFragment → View_Model → MovieRepository → API Service → UI Update
2. **Library Flow**: User Action → LibraryFragment → View_Model → MovieRepository → Room Database → UI Update
3. **State Management**: UI State → SharedViewModel → Preserved across navigation/configuration changes

## 🎨 UI/UX Features

- **Material Design** components and guidelines
- **Responsive Grid Layout** for different screen sizes
- **Custom Dialogs** for movie details
- **Loading States** with progress indicators
- **Error Handling** with user-friendly messages
- **Keyboard Management** for search functionality


## 🤝 Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/AmazingFeature`)
3. Commit your changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.