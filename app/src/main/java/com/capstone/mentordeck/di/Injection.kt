package com.capstone.mentordeck.di

import com.capstone.mentordeck.database.MentorDao
import com.capstone.mentordeck.repository.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object Injection {

    @Provides
    @ViewModelScoped
    fun provideRepository(mentorDao: MentorDao) = UserRepository(mentorDao)
}