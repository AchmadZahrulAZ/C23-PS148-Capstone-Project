package com.capstone.mentordeck.di

import android.app.Application
import androidx.room.Room
import com.capstone.mentordeck.database.MentorDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Provider {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, MentorDatabase::class.java, "mtr.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: MentorDatabase) = database.mentorDao()
}