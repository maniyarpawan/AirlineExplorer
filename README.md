# ✈️ Airline Explorer App

Airline Explorer is an Android application that displays a list of airlines with details including name, country, headquarters, fleet size, and logo. Users can mark airlines as favorites and search airlines by name or country.

---

## 🏗 Architecture Overview

This project follows a **Clean Architecture** approach with layers separated into:

- **Presentation Layer**:  
  Jetpack Compose UI + ViewModel + StateHolder (MVVM-based)
  
- **Domain Layer**:  
  Contains UseCases that represent business logic, independent of framework or platform.

- **Data Layer**:  
  Repositories interact with local (Room) and remote (Retrofit) data sources.

- **Data Sources**:
  - **Remote**: Fetch data from a REST API
  - **Local**: Store and retrieve data from Room database

- **DI**:  
  Hilt is used for dependency injection throughout the app.

---

## 🧰 Tools & Libraries Used

| Purpose            | Library / Tool |
|--------------------|----------------|
| UI                 | Jetpack Compose, Material3 |
| Architecture       | MVVM + Clean Architecture |
| Dependency Injection | Hilt |
| Networking         | Retrofit |
| Serialization      | Gson |
| Local Storage      | Room (SQLite) |
| Image Loading      | Coil |
| Coroutines         | Kotlin Coroutines, Flows |
| Navigation         | Jetpack Navigation (Compose) |

---

## 🚀 Build & Run Instructions

### ✅ Prerequisites
- Android Studio Ladybug or later
- Kotlin 1.9+
- Gradle 8.0+
- Android SDK 33+

### 🛠 Steps

1. Clone the repository:
   ```bash
   [git clone https://github.com/maniyarpawan/airline-explorer.git](https://github.com/maniyarpawan/AirlineExplorer.git)
