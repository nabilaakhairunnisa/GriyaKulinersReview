# Griya Kuliner's Review
Griya Kuliner's Review App is a platform developed for Griya Kuliner's cafe to facilitate customers in giving reviews about the food and beverages they offer. This application aims to improve customer engagement and provide valuable feedback to Griya Kuliner's management.

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
  - `implementation("com.google.dagger:hilt-android:2.51.1")`: Hilt core library for Android.
  - `kapt("com.google.dagger:hilt-android-compiler:2.51.1")`: Annotation processor for Hilt on Android.
  - `kapt("com.google.dagger:hilt-compiler:2.51.1")`: Annotation processor for Hilt.
- **Glide**: Glide is an image loading and caching library for Android that provides smooth and efficient image loading. It helps optimize image loading performance and supports various image formats.
  - `implementation("com.github.bumptech.glide:glide:4.15.1")`: Glide library for image loading.
- **AndroidX Lifecycle & Activity**: AndroidX components are used for lifecycle management and activity handling in the application.
  - `implementation("androidx.lifecycle:lifecycle-livedata-ktx:2.7.0")`: LiveData Kotlin extensions for lifecycle-aware components.
  - `implementation("androidx.activity:activity-ktx:1.8.2")`: Activity Kotlin extensions for lifecycle-aware components.

### Backend
- **Firebase Realtime Database**: Data is stored and synchronized in real-time using Firebase Realtime Database, allowing for efficient data management and retrieval.
  - `implementation("com.google.firebase:firebase-database:20.3.1")`: Firebase Realtime Database library.
- **Firebase Storage**: Images and other media assets are stored securely using Firebase Storage, ensuring scalability and reliability.
  - `implementation("com.google.firebase:firebase-storage:20.3.0")`: Firebase Storage library.
- **Firebase Authentication**: User authentication is managed using Firebase Authentication, providing secure and seamless login sessions.
  - `implementation("com.google.firebase:firebase-auth:22.3.1")`: Firebase Authentication library.

## Installation and Setup
To run the Griya Kuliner's Review App locally, follow these steps:
1. Clone this repository to your local machine.
2. Open the project in Android Studio.
3. Connect the project to your Firebase project using Firebase Assistant in Android Studio.
4. Add the necessary `google-services.json` file to your project for Firebase configuration.
5. Ensure that the required dependencies are added to your Gradle files.
6. Build and run the application on an Android emulator or physical device.

## Contributing
Contributions to the Griya Kuliner's Review App are welcome! If you have any suggestions, bug fixes, or new features to propose, please feel free to open an issue or submit a pull request.

## Download Link
https://drive.google.com/file/d/1TVIv5tNyJDNFRduiwmPCWz1LaPbZIdff/view?usp=drive_link

## Reference
https://youtube.com/playlist?list=PLIIWAqaTrNlg7q0cfajkBj8OwG60qpBVL&feature=shared

## License
This project is licensed under the [MIT License](LICENSE).
