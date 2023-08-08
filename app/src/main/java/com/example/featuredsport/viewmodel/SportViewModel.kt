package com.example.featuredsport.viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.featuredsport.data.model.Sport
import com.example.featuredsport.data.model.SportUiState
import com.example.featuredsport.domain.GetRandomSport
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

/**
 * Sport view model
 *
 * @property getRandomSport
 */
@HiltViewModel
class SportViewModel @Inject constructor(
    private val getRandomSport: GetRandomSport
) : ViewModel() {

    private val _uiState = MutableStateFlow(SportUiState(null, false))
    val uiState: StateFlow<SportUiState> = _uiState

    init {
        println("refreshSport init")
        refreshSport()
    }

    private fun updateState(transform: (SportUiState) -> SportUiState) {
        _uiState.value = transform(_uiState.value)
    }

    private fun setLoading(isLoading: Boolean) {
        updateState { it.copy(loading = isLoading) }
    }

    private fun setSport(sport: Sport) {
        updateState { it.copy(currentSport = sport, loading = false) }
    }

    fun refreshSport() {
        viewModelScope.launch {

            println("set Loading true")
            setLoading(true)

            val newSport = withContext(Dispatchers.IO) {
                getRandomSport()
            }

            println("newSport $newSport")
            setSport(newSport)
        }
    }
}