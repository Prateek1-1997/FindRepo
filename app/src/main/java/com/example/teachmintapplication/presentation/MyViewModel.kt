package com.example.teachmintapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachmintapplication.domain.IRepository
import com.example.teachmintapplication.domain.Item
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MyViewModel @Inject constructor(val iRepository: IRepository):ViewModel() {


    var job by mutableStateOf<Job?>(null)
    private val debounceTimeMillis = 1000L // Adjust the delay as needed


  private val _uiState = MutableStateFlow(State(repoList = null))
    val uiState: StateFlow<State> = _uiState.asStateFlow()

    var pageCount=1
    var query:String =""



  fun searchRepo(query: String){
      job?.cancel()
     job= viewModelScope.launch {
          this@MyViewModel.query=query
          delay(debounceTimeMillis)
         pageCount=1
          val repoData = iRepository.getRepoList(query,pageCount)
          _uiState.update { currentState->
              currentState.copy(
                  repoList = repoData,
              )
          }

      }
  }

    fun getMoreRepo() {
        viewModelScope.launch {
            pageCount++
            val newData = iRepository.getRepoList(query,pageCount)
            _uiState.update { currentState ->
                currentState.copy(
                    repoList = getUpdatedList(_uiState.value.repoList,newData)
                )
            }

        }
    }

    private fun getUpdatedList(repoList: List<Item>?, newData: List<Item>?): List<Item>? {
        return when {
            repoList == null && newData == null -> null
            repoList == null -> newData
            newData == null -> repoList
            else -> repoList + newData
        }
    }





    data class State(
     val repoList: List<Item>?,
 )

}