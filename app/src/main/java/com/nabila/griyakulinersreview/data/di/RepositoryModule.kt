package com.nabila.griyakulinersreview.data.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.storage.FirebaseStorage
import com.nabila.griyakulinersreview.data.repository.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {
    @Provides
    @Singleton
    fun provideAuthRepository(auth: FirebaseAuth, db: FirebaseDatabase): AuthRepository {
        return AuthRepositoryImpl(auth, db)
    }

    @Provides
    @Singleton
    fun provideMenuRepository(db: FirebaseDatabase, storage: FirebaseStorage): MenuRepository {
        return MenuRepositoryImpl(db, storage)
    }
}