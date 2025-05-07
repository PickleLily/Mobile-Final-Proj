package com.bignerdranch.android.final_proj

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.bignerdranch.android.final_proj.database.ScoreDao
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import java.io.File
import javax.inject.Inject

@HiltViewModel
class ScoreListViewModel @Inject constructor(private val scoreDao: ScoreDao): ViewModel() {

    private val listEventChannel = Channel<ListEvent>()

    val listEvent = listEventChannel.receiveAsFlow()
    val scoreListLiveData = scoreDao.getScores()

    fun onAddNewScoreClick() = viewModelScope.launch(Dispatchers.IO) {
        listEventChannel.send(ListEvent.NavigateToAddItemScreen)
    }

    fun onAddNewScoreClick(score: Score) = viewModelScope.launch(Dispatchers.IO) {
        listEventChannel.send(ListEvent.NavigateToEditItemScreen(score))
    }

    fun deleteAllScores(filesDir: File) = viewModelScope.launch(Dispatchers.IO) {
        scoreDao.deleteAllScores()
        filesDir.listFiles()?.forEach { it.delete() }
    }

    sealed class ListEvent {
        object NavigateToAddItemScreen : ListEvent()
        data class NavigateToEditItemScreen(val score: Score) : ListEvent()
    }


}