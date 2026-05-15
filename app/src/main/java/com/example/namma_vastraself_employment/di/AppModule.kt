package com.example.namma_vastraself_employment.di

import android.content.Context
import androidx.room.Room
import com.example.namma_vastraself_employment.data.local.AppDatabase
import com.example.namma_vastraself_employment.data.local.dao.TrendDao
import com.example.namma_vastraself_employment.data.repository.AuthRepositoryImpl
import com.example.namma_vastraself_employment.data.repository.SareeRepositoryImpl
import com.example.namma_vastraself_employment.data.repository.TrendRepositoryImpl
import com.example.namma_vastraself_employment.domain.repository.AuthRepository
import com.example.namma_vastraself_employment.domain.repository.SareeRepository
import com.example.namma_vastraself_employment.domain.repository.TrendRepository
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideFirebaseAuth(): FirebaseAuth = FirebaseAuth.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseFirestore(): FirebaseFirestore = FirebaseFirestore.getInstance()

    @Provides
    @Singleton
    fun provideFirebaseStorage(): FirebaseStorage = FirebaseStorage.getInstance()

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context,
            AppDatabase::class.java,
            "namma_vastra_db"
        ).build()
    }

    @Provides
    fun provideTrendDao(database: AppDatabase): TrendDao = database.trendDao()

    @Provides
    @Singleton
    fun provideAuthRepository(
        auth: FirebaseAuth,
        firestore: FirebaseFirestore
    ): AuthRepository = AuthRepositoryImpl(auth, firestore)

    @Provides
    @Singleton
    fun provideSareeRepository(
        firestore: FirebaseFirestore,
        storage: FirebaseStorage
    ): SareeRepository = SareeRepositoryImpl(firestore, storage)

    @Provides
    @Singleton
    fun provideTrendRepository(
        firestore: FirebaseFirestore,
        trendDao: TrendDao
    ): TrendRepository = TrendRepositoryImpl(firestore, trendDao)
}
