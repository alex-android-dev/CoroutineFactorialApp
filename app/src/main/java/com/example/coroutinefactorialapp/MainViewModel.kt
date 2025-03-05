package com.example.coroutinefactorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

data class State(
    val isError: Boolean = false,
    val isInProgress: Boolean = false,
    val factorial: String = "",
)

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>(State())

    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {

        _state.value = state.value?.copy(
            isInProgress = true
        )


        if (value.isNullOrBlank()) {
            _state.value = state.value?.copy(
                isInProgress = false,
                isError = true
            )
            return
        }

        val number = value.toLong()
        // todo calculate

        viewModelScope.launch {
            delay(1000)
            _state.value = state.value?.copy(
                isInProgress = false,
                factorial = number.toString()
            )
        }


    }


}