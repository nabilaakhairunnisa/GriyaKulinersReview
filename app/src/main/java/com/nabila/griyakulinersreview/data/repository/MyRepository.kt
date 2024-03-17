//package com.nabila.griyakulinersreview.data.repository
//
//
//import com.google.firebase.database.DatabaseReference
//import com.google.firebase.database.FirebaseDatabase
//import com.google.firebase.storage.FirebaseStorage
//import com.google.firebase.storage.StorageReference
//import com.nabila.griyakulinersreview.data.model.MenuMakanan
//import com.nabila.griyakulinersreview.data.preference.OnFirestoreTaskComplete
//
//class QuizListRepository(private val onFirestoreTaskComplete: OnFirestoreTaskComplete) {
//
//    val storageRef: StorageReference = FirebaseStorage.getInstance().getReference("foto makanan")
//    val databaseRef: DatabaseReference = FirebaseDatabase.getInstance().getReference("menu")
//
//    fun getQuizData() {
//        databaseRef.get().addOnCompleteListener { task ->
//            if (task.isSuccessful) {
//                val quizList = mutableListOf<MenuMakanan>()
//                for (document in task.result!!) {
//                    val quizModel = document.toObject<QuizListModel>()
//                    quizList.add(quizModel)
//                }
//                onFirestoreTaskComplete.quizDataLoaded(quizList)
//            } else {
//                onFirestoreTaskComplete.onError(task.exception!!)
//            }
//        }
//}
