package com.capstone.mentordeck.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class UserModel (
    val id: Int,
    val name: String,
    val subject: String,
    val price: String,
    val location: String,
    val rate: String,
    val review: String,
    val image: Int

) : Parcelable