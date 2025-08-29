# Griya Kuliner's Review
Griya Kuliner's Review App is a platform developed for a local MSME that enables customers to review food and beverages, aiming to improve product quality and service.

<img src="https://github.com/nabilaakhairunnisa/GriyaKulinersReview/blob/master/banner.png"
     alt="Banner" /> 

## Features
- **User Authentication**: Users can create an account and log in securely to the application.
- **Menu Exploration**: Customers can explore the menu offered by Griya Kuliner's cafe.
- **Review Submission**: Users can submit reviews for the food and beverages they have tried.
- **Rating System**: Reviews can include a rating to indicate the user's satisfaction level.
- **Real-time Feedback**: Griya Kuliner's management can view and respond to customer reviews in real-time.
- **Menu Management**: Admin can add, edit, and delete menu items to update the offerings.


### Frontend
- **Android Studio with Kotlin**: The frontend of the application is built using Kotlin programming language in Android Studio, providing a native Android experience for users.
- **Dagger Hilt**: Dagger Hilt is a dependency injection library for Android that reduces boilerplate code and facilitates modularization. It helps manage dependencies efficiently and promotes better app architecture.
- **Glide**: Glide is an image loading and caching library for Android that provides smooth and efficient image loading. It helps optimize image loading performance and supports various image formats.
- **AndroidX Lifecycle & Activity**: AndroidX components are used for lifecycle management and activity handling in the application.

### Backend
- **Firebase Authentication**: User authentication is managed using Firebase Authentication, providing secure and seamless login sessions.
- **Firebase Realtime Database**: Data is stored and synchronized in real-time using Firebase Realtime Database, allowing for efficient data management and retrieval.
- **Firebase Storage**: Images and other media assets are stored securely using Firebase Storage, ensuring scalability and reliability.

## How to Install the APK
To quickly try the Griya Kuliner's Review App on your Android device:
1. Download the latest APK release from [this link](https://github.com/nabilaakhairunnisa/GriyaKulinersReview/raw/refs/heads/master/griya-kuliner-review.apk).
2. On your device, go to **Settings > Security** and enable **Install from Unknown Sources** (if not already enabled).
3. Open the downloaded APK file.
4. Tap **Install** and wait until the process is complete.
5. Once installed, open the app and enjoy!

## Installation and Setup (For Developers)
If you want to run or modify the project in Android Studio, follow these steps:
1. Clone this repository to your local machine.
2. Open the project in **Android Studio**.
3. Connect the project to your **Firebase project** using Firebase Assistant in Android Studio.
4. Add the required `google-services.json` file for Firebase configuration.
5. Make sure all necessary dependencies are included in your Gradle files.
6. Build and run the application on an emulator or physical device.

## License
This project is licensed under the [MIT License](LICENSE).
