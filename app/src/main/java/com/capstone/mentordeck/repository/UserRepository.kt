package com.capstone.mentordeck.repository

import com.capstone.mentordeck.database.MentorDao
import com.capstone.mentordeck.database.MentorEntity
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class UserRepository @Inject constructor(private val mentorDao: MentorDao) {

    fun getAllMentor() = mentorDao.getAllMentor()

    suspend fun insertAllMentor(mentorList: List<MentorEntity>) = mentorDao.insertAllMentor(mentorList)
}