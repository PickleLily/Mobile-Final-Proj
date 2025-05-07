package com.bignerdranch.android.final_proj

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.final_proj.database.ScoreDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.util.*
import javax.inject.Inject

@HiltViewModel
class CrimeViewModel @Inject constructor(
    private val scoreDao: ScoreDao,
    private val state: SavedStateHandle
) : ViewModel() {

    val score = state.get<Score>("score")

    var scoreUsername = state.get<String>("user") ?: score?.user ?: ""
        set(value) {
            field = value
            state.set("crimeTitle", value)
        }

    var scoreDate = state.get<Date>("date") ?: score?.date ?: Date()
        set(value) {
            field = value
            state.set("crimeDate", value)
        }

    var scoreScore = state.get<Boolean>("score") ?: score?.score ?: 0
        set(value) {
            field = value
            state.set("crimeSolved", value)
        }

    fun onSaveClick() {
        if (score != null) {
            val updatedCrime = score.copy(
                user = scoreUsername,
                date = scoreDate,
                score = scoreScore as Int,
            )
            saveScore(updatedCrime)
        } else {
            val newScore = Score(
                user = scoreUsername,
                date = scoreDate,
                score = scoreScore as Int
            )
            addNewScore(newScore)
        }
    }

    fun onDeleteClick() {
        if (score != null) deleteScore(score)
    }

    private fun saveScore(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        scoreDao.updateScore(score)
    }

    private fun addNewScore(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        scoreDao.addScore(score)
    }

    private fun deleteScore(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        scoreDao.deleteScore(score)
    }
}class ScoreViewModel {
}