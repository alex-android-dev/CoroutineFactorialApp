package com.example.coroutinefactorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineName
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.math.BigInteger

class MainViewModel : ViewModel() {

    private val _state = MutableLiveData<State>()

    private val myCoroutineScope = CoroutineScope(
        Dispatchers.Main +
                CoroutineName("myCoroutineScope")
    )

    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {
        _state.value = Progress

        if (value.isNullOrBlank()) {
            _state.value = Error
            return
        }

        myCoroutineScope.launch {
            val number = value.toLong()
            val result = withContext(Dispatchers.Default) {
                factorial(number)
            }
            _state.value = Factorial(result)
        }
    }

    override fun onCleared() {
        myCoroutineScope.cancel()
        super.onCleared()
    }

    private fun factorial(number: Long): String {
        var result = BigInteger.ONE

        for (i in 1..number) {
            result = result.multiply(BigInteger.valueOf(i))
        }

        return result.toString()
    }


}