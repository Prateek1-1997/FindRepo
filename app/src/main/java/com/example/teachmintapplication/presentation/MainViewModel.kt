package com.example.teachmintapplication.presentation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.teachmintapplication.domain.usecase.FetchRepositoryUsecase
import com.example.teachmintapplication.domain.remote.IRepository
import com.example.teachmintapplication.domain.models.RepositoryItem
import com.example.teachmintapplication.domain.models.RepositoryDetailDto
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val iRepository: IRepository, private val fetchRepositoryUsecase: FetchRepositoryUsecase):ViewModel() {


    private var job by mutableStateOf<Job?>(null)
    private val debounceTimeMillis = 1000L

    private val coroutineExceptionHandler = CoroutineExceptionHandler { _, throwable ->
        throwable.printStackTrace()
    }


  private val _uiState = MutableStateFlow(State(repositoryItemList = null))
    val uiState: StateFlow<State> = _uiState.asStateFlow()


    private val _repositoryDetailsState = MutableStateFlow(RepositoryDetailsState(repository = null))
    val repositoryDetailsState: StateFlow<RepositoryDetailsState> = _repositoryDetailsState.asStateFlow()

    private var pageCount=1
    private var query:String =""



  fun searchRepositories(query: String){
      job?.cancel()
      job= viewModelScope.launch (coroutineExceptionHandler){
          this@MainViewModel.query=query
          delay(debounceTimeMillis)
          pageCount=1
          val repoData = fetchRepositoryUsecase.invoke(query,pageCount)
          _uiState.update { currentState->
              currentState.copy(
                  repositoryItemList = repoData,
              )
          }

      }
  }

    fun getMoreRepositories() {
            viewModelScope.launch (coroutineExceptionHandler){
            pageCount++
            val repositoryItems = iRepository.getRepositoriesList(query,pageCount)
            _uiState.update { currentState ->
                currentState.copy(
                    repositoryItemList = getUpdatedList(_uiState.value.repositoryItemList,repositoryItems)
                )
            }

        }
    }


    fun getRepositoryDetails(repoName:String) {
            viewModelScope.launch (coroutineExceptionHandler){
            val repoData = iRepository.getRepositoryDetails(repoName)
            _repositoryDetailsState.update { currentState ->
                currentState.copy(
                    repository = repoData
                )
            }

        }
    }

    private fun getUpdatedList(repositoryItemList: List<RepositoryItem>?, repositoryItems: List<RepositoryItem>?): List<RepositoryItem>? {
        return when {
            repositoryItemList == null && repositoryItems == null -> null
            repositoryItemList == null -> repositoryItems
            repositoryItems == null -> repositoryItemList
            else -> repositoryItemList + repositoryItems
        }
    }




    data class State(
        val repositoryItemList: List<RepositoryItem>?,
 )

    data class RepositoryDetailsState(
        val repository: RepositoryDetailDto?,
    )

}