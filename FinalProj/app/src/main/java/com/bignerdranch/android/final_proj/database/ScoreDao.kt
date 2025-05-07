package com.bignerdranch.android.final_proj.database
import androidx.lifecycle.LiveData
import androidx.room.*
import com.bignerdranch.android.final_proj.Score

@Dao
interface ScoreDao  {
    @Query("SELECT * FROM leaderboard")
    fun getScores(): LiveData<List<Score>>

    @Update
    suspend fun updateScore(score: Score)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addScore(score: Score): Long

    @Delete
    suspend fun deleteScore(score: Score)

    @Query("DELETE FROM leaderboard")
    suspend fun deleteAllScores()
}