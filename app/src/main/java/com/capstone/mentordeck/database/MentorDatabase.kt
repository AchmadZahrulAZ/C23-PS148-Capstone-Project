package com.capstone.mentordeck.database

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [MentorEntity::class],
    version = 1,
    exportSchema = false
)
abstract class MentorDatabase : RoomDatabase() {
    abstract fun mentorDao(): MentorDao
    abstract fun remoteKeysDao(): RemoteKeysDao
}