package com.example.coroutinefactorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress

        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        val number = value.toLong()
        // todo calculate

        viewModelScope.launch {
            delay(1000)
            _state.value = Result(number.toString())
        }
    }


}