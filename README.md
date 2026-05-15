# Namma Vastra - Weaver to Market Platform

**Namma Vastra** is a production-level Android application designed to bridge the gap between traditional handloom weavers (specifically from the Ilkal and Molakalmuru regions of Karnataka) and urban markets.

## 🌟 Project Goal
The primary objective is to empower weavers by providing them with a direct-to-consumer platform where they can showcase their craftsmanship, stay updated with fashion trends, and calculate fair retail prices for their products.

## 🚀 Key Features

### 1. Trend Board
- A Pinterest-style masonry grid showing trending colors, modern saree designs, and seasonal inspirations.
- Visual color palettes for weavers to adapt their designs to modern tastes.
- Shimmer loading and offline caching support.

### 2. Marketplace Gallery (Loom Gallery)
- A comprehensive showcase of available handloom sarees.
- High-quality image previews and detailed descriptions.
- **WhatsApp Integration**: Direct deep link to contact the weaver for inquiries.
- Status tracking (Available/Sold).

### 3. Saree Upload Portal
- Simple interface for weavers to list their new creations.
- Support for multiple image uploads with compression below 1MB to save storage.
- Real-time sync with Firebase Firestore.

### 4. Fair Price Calculator
- A utility for weavers to calculate a Fair Retail Price (FRP) based on material costs and labor hours.
- **Formula**: `FRP = (MaterialCost + (WeavingHours × 50)) × 1.15 × 1.5`
- Detailed cost breakdown and visual results.

### 5. Weaver Story & Heritage
- Article-style screens preserving the history and techniques of Ilkal and Molakalmuru weaving.
- Educational content about the GI (Geographical Indication) tags and traditional motifs.

### 6. Robust Profile Management
- User profile creation for weavers including craft specialty, experience, and location.
- "My Listings" section to manage their uploaded sarees.

## 🛠 Tech Stack

- **UI**: Jetpack Compose (Material 3)
- **Architecture**: MVVM + Clean Architecture
- **Language**: Kotlin + Coroutines/Flow
- **Dependency Injection**: Hilt
- **Database (Local)**: Room Database (for offline caching of trends)
- **Database (Cloud)**: Firebase Firestore
- **Storage**: Firebase Cloud Storage
- **Authentication**: Firebase Phone Auth (Mocked for immediate testing)
- **Image Loading**: Coil (with shimmer placeholders and error fallbacks)
- **Navigation**: Jetpack Navigation Compose

## 📁 Project Structure
- `data/`: Repositories and Local/Remote data sources.
- `domain/`: Business logic models and repository interfaces.
- `presentation/`: Compose screens, ViewModels, and navigation logic.
- `di/`: Hilt Dependency Injection modules.
- `ui/theme/`: Custom handloom-inspired Material 3 theme.

## ⚙️ Setup Instructions

### Firebase Configuration
1. Create a new project in the [Firebase Console](https://console.firebase.google.com/).
2. Add an Android app with package name `com.example.namma_vastraself_employment`.
3. Download the `google-services.json` file.
4. Place `google-services.json` into the `app/` directory of this project.
5. In the Firebase Console:
   - Enable **Firestore Database** in Test Mode.
   - Enable **Storage** and set rules to public (for development).
   - Enable **Phone Authentication**.

### Building the Project
1. Open the project in **Android Studio (Ladybug or newer)**.
2. Sync the project with Gradle files.
3. Ensure the `google-services` plugin is active in `app/build.gradle.kts`.
4. Click **Run** on a physical device or emulator.

## 🎨 Design Philosophy
The app uses an elegant palette of **Primary (#8B2252)** and **Secondary (#D4AF37)**, inspired by the rich silk and gold zari of traditional Karnataka sarees. The UI is minimal, modern, and focused on showcasing the intricate details of the fabric.

## 🤝 Contributing
Contributions are welcome! If you find any issues or have feature suggestions, please open an issue or submit a pull request.

---
*Developed to preserve heritage and empower artisans.*
