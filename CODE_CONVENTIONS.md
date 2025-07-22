# ANAM Wallet V2 - Code Conventions

## 🏗️ Architecture Overview

This project follows **Clean Architecture** principles with a **multi-module** structure, ensuring separation of concerns, testability, and scalability.

### Module Structure

```
v2-anamwallet/
├── app/                          # Main application module
│   ├── MainActivity              # Entry point with SplashScreen API
│   ├── navigation/               # Navigation components
│   │   ├── AnamNavHost          # Navigation graph
│   │   ├── AnamNavRoute         # Type-safe routes
│   │   ├── AnamBottomNavigation # Bottom navigation bar
│   │   └── NavigationConfig     # Navigation configuration
│   └── ui/                      # App-specific UI
│       ├── components/          # App-only components (e.g., Header)
│       ├── theme/               # Theme ViewModel
│       └── language/            # Language ViewModel
│
├── core/
│   ├── common/                  # Pure Kotlin module (no Android deps)
│   │   ├── model/               # Shared domain models
│   │   │   ├── Language.kt      # Language enum
│   │   │   ├── ThemeMode.kt     # Theme enum
│   │   │   ├── MiniApp.kt       # MiniApp domain model
│   │   │   └── MiniAppType.kt   # MiniApp type enum
│   │   └── result/              # Custom Result types
│   │       └── MiniAppResult.kt # Type-safe result handling
│   │
│   ├── data/                    # Data layer utilities
│   │   └── datastore/           # DataStore preferences
│   │
│   └── ui/                      # Shared UI resources
│       ├── theme/               # Material3 theme definitions
│       │   ├── Color.kt         # Color palette
│       │   ├── Type.kt          # Typography (16sp titleMedium)
│       │   └── Shape.kt         # Shape definitions
│       └── language/            # Language support
│           └── LocalStrings.kt  # CompositionLocal & strings
│
└── feature/                     # Feature modules
    ├── main/                    # Home/Dashboard
    ├── miniapp/                 # MiniApp management
    │   ├── webapp/              # WebApp specific
    │   ├── blockchain/          # Blockchain specific
    │   └── common/              # Shared components
    ├── hub/                     # Service hub
    ├── browser/                 # Web browser
    ├── identity/                # Digital ID management
    └── settings/                # App settings
```

### Module Dependencies

```
app ─────────┬──→ core:common
             ├──→ core:ui
             ├──→ core:data
             └──→ all features

features ────┬──→ core:common
             ├──→ core:ui
             └──→ core:data

core:ui ─────→ core:common
core:data ───→ core:common

core:common  (no dependencies - pure Kotlin)
```

## 📁 Standard Feature Module Structure

Each feature module follows a consistent Clean Architecture structure:

```
feature/{name}/
├── ui/                              # Presentation Layer
│   ├── {Name}Screen.kt              # Compose UI
│   ├── {Name}ViewModel.kt           # State management
│   ├── {Name}Contract.kt            # MVI Contract (State, Intent, Effect)
│   └── components/                  # Feature-specific UI components
│
├── domain/                          # Business Layer
│   ├── model/                       # Feature-specific models
│   ├── repository/                  # Repository interfaces
│   └── usecase/                     # Business logic (one per action)
│       ├── Get{Name}UseCase.kt      # Query operations
│       └── Set{Name}UseCase.kt      # Command operations
│
├── data/                            # Data Layer
│   ├── local/                       # Local data sources (renamed from 'source')
│   └── repository/                  # Repository implementations
│       └── {Name}RepositoryImpl.kt
│
└── di/                              # Dependency Injection
    └── {Name}Module.kt              # Hilt module for bindings
```

## 🔄 Architecture Flow

### MVI Pattern with Unidirectional Data Flow

```
┌─────────────────┐
│   User Action   │
└────────┬────────┘
         ▼
┌─────────────────┐     ┌──────────────┐
│   UI (Screen)   │────▶│  Contract    │
└────────┬────────┘     │  - State     │
         │              │  - Intent    │
         ▼              │  - Effect    │
┌─────────────────┐     └──────────────┘
│   ViewModel     │
│   _uiState      │ (StateFlow)
│   _effect       │ (SharedFlow)
└────────┬────────┘
         ▼
┌─────────────────┐
│    UseCase      │ (Business Logic)
└────────┬────────┘
         ▼
┌─────────────────┐
│   Repository    │ (Interface)
│   (Domain)      │
└────────┬────────┘
         ▼
┌─────────────────┐
│ Repository Impl │ (Implementation)
│    (Data)       │
└────────┬────────┘
         ▼
┌─────────────────┐
│   Data Source   │ (Local/Remote)
└─────────────────┘
```

## 🎯 Key Architectural Patterns

### 1. MVI Pattern

Full MVI implementation with:

- **State**: Single immutable state object per screen
- **Intent**: User actions as sealed interfaces
- **Effect**: One-time events using SharedFlow (Google recommended)

```kotlin
// ViewModel pattern
private val _uiState = MutableStateFlow(Contract.State())
val uiState: StateFlow<Contract.State> = _uiState.asStateFlow()

private val _effect = MutableSharedFlow<Contract.Effect>(
    replay = 0,
    extraBufferCapacity = 1,
    onBufferOverflow = BufferOverflow.DROP_OLDEST
)
val effect: SharedFlow<Contract.Effect> = _effect.asSharedFlow()
```

### 2. Clean Architecture Layers

- **UI Layer**: Compose screens and ViewModels
- **Domain Layer**: Business logic (UseCases) and repository interfaces
- **Data Layer**: Repository implementations and data sources

### 3. Multi-Module Benefits

- **Parallel Development**: Teams can work on different features
- **Faster Builds**: Only changed modules rebuild
- **Clear Boundaries**: Enforced separation of concerns
- **Reusability**: Features can be shared across apps

### 4. Consistent Naming Conventions

- **State management**: `_uiState` / `uiState` (not `_state`)
- **Effect handling**: SharedFlow instead of Channel
- **Folder structure**: `local` instead of `source` for data sources

## 🔧 Development Guidelines

### Creating a New Feature Module

1. **Module Setup**

   ```
   feature/{name}/
   ├── build.gradle.kts
   └── src/main/java/com/anam145/wallet/feature/{name}/
   ```

2. **Define Contract**

   ```kotlin
   interface {Name}Contract {
       data class State(...)
       sealed interface Intent { ... }
       sealed interface Effect { ... }
   }
   ```

3. **Implement ViewModel**
   ```kotlin
   @HiltViewModel
   class {Name}ViewModel @Inject constructor(
       private val useCase: {Name}UseCase
   ) : ViewModel() {
       private val _uiState = MutableStateFlow(Contract.State())
       val uiState = _uiState.asStateFlow()

       private val _effect = MutableSharedFlow<Contract.Effect>()
       val effect = _effect.asSharedFlow()

       fun processIntent(intent: Contract.Intent) { ... }
   }
   ```

### Code Conventions

#### Naming Conventions

- **Screens**: `{Feature}Screen.kt`
- **ViewModels**: `{Feature}ViewModel.kt` with `_uiState`/`uiState`
- **Contracts**: `{Feature}Contract.kt` with State, Intent, Effect
- **UseCases**: `{Action}{Feature}UseCase.kt`
- **Data sources**: Place in `local/` folder (not `source/`)

#### Architecture Rules

- **MVI Pattern**: Use Contract pattern for all ViewModels
- **Effect Handling**: Use SharedFlow (not Channel)
- **Error Handling**: Use custom MiniAppResult sealed interface
- **Constants**: Centralize in dedicated files
- **Result Type**: Use MiniAppResult for type-safe error handling
- **Initialization**: Handle in MainViewModel with SplashScreen API

## 🧭 Navigation System

The app uses Jetpack Navigation Compose with type-safe routes:

### Navigation Components

- **AnamNavRoute**: Sealed class defining all app destinations
- **AnamNavHost**: Central navigation graph composable
- **AnamBottomNavigation**: Bottom navigation bar with 5 main destinations
- **NavigationConfig**: Centralized navigation configuration

### Navigation Flow

```
MainActivity (with SplashScreen)
    │
    ├── SplashScreen (shows during initialization)
    │
    └── After initialization:
        ├── Header (App bar)
        ├── AnamNavHost (Content)
        │   ├── MainScreen (with MiniApp list)
        │   ├── HubScreen
        │   ├── BrowserScreen
        │   ├── IdentityScreen
        │   └── SettingsScreen
        │
        └── AnamBottomNavigation (Bottom bar)
```

Navigation handles proper back stack management with `popUpTo`, `saveState`, and `restoreState`.

## 🎨 UI/UX Features

### Material Design 3

- Custom theme with Cocogoose font for headlines
- Typography: `titleMedium` = 16sp (matching anam-android)
- Consistent color scheme with `surfaceVariant` for backgrounds
- Shape system with `ShapeCard` (20dp rounded corners)

### MiniApp System

- Dynamic loading from assets/miniapps folder
- ZIP file support with manifest.json
- Icon loading with fallback support (Material Icons)
- Blockchain apps with activation state
- Grid layout for regular apps (3 columns)

### Visual Consistency

- `Arrangement.SpaceBetween` for blockchain cards
- `FontWeight.SemiBold` for titles
- Consistent spacing and padding
- Smooth animations with spring() and animateColorAsState

## Real-time Language System

The app supports instant language switching without Activity restart using CompositionLocal:

```kotlin
// Access strings in any Composable
val strings = LocalStrings.current
Text(text = strings.welcomeMessage)
```

## MiniApp vs WebApp vs Blockchain Naming Convention

### MiniApp (Common Concept)
- Collective term for all mini applications
- Common features like manifest, file management
- Examples: `MiniAppManifest`, `MiniAppFileManager`

### WebApp (General Web Apps)
- General web applications like Government24
- Runs in :app process
- Examples: `WebAppActivity`, `WebAppService`

### Blockchain (Blockchain Apps)
- Blockchain applications like Ethereum, MetaMask
- Runs in :blockchain process
- Requires active state management
- Examples: `BlockchainUIActivity`, `BlockchainService`

## Multi-Process Architecture

### Process Separation
- **:main process**: Main process (most app features)
- **:app process**: WebApp dedicated process
- **:blockchain process**: Blockchain dedicated process

### IPC Communication
- Inter-process communication via AIDL
- `IWebAppService`: WebApp → Blockchain communication
- `IBlockchainService`: Blockchain service interface

## 📦 Tech Stack

### Core

- **Jetpack Compose**: Modern declarative UI
- **Hilt**: Compile-time dependency injection
- **Coroutines & Flow**: Asynchronous programming
- **DataStore**: Modern data persistence

### Architecture Components

- **ViewModel**: UI state management with MVI
- **Navigation Compose**: Type-safe navigation (2.7.7)
- **StateFlow & SharedFlow**: Observable state holders

### UI

- **Material 3**: Latest design system
- **Material Icons Extended**: Comprehensive icon set
- **Compose Animation**: Smooth transitions