package com.capstone.mentordeck.database

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface MentorDao {

    @Query("SELECT * FROM mtr")
    fun getAllMentor(): Flow<List<MentorEntity>>

    @Query("SELECT * FROM mtr WHERE id = :id")
    fun getMentorById(id: Int): Flow<MentorEntity>

//    @Query("SELECT * FROM mtr WHERE isFavorite = 1")
//    fun getAllFavoriteMentor(): Flow<List<MentorEntity>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllMentor(mentorList: List<MentorEntity>)

//    @Query("UPDATE mtr SET isFavorite = :isFavorite WHERE id = :id")
//    suspend fun updateFavoriteMentor(id: Int, isFavorite: Boolean)

//    @Query("SELECT * FROM mtr WHERE subject LIKE '%' || :query || '%'")
//    fun searchMentor(query: String): Flow<List<MentorEntity>>

    @Query("DELETE FROM mtr")
    suspend fun deleteAll()

}