package com.capstone.mentordeck.database

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "mtr")
data class MentorEntity (
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val name: String,
    val subject: String,
    val price: String,
    val location: String,
    val rate: String,
    val review: String,
    val image: Int

)