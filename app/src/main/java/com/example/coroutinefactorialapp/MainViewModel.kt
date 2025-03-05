package com.example.coroutinefactorialapp

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

open class State(

    // Теперь нам не нужны поля класса выше
//    val isError: Boolean = false,
//    val isInProgress: Boolean = false,

    // Тут тоже удаляем поле, т.к. есть отдельный класс для него
//    val factorial: String = "",
)

// Данный класс представляет собой состояние экрана с ошибкой
// Если прилетел класс Error, то значит ошибка равна true
class Error: State()

// В данном случае мы заменяем поле isInProgress классом Progress
class Progress: State()

class Result(
    val factorial: String,
) : State()

class MainViewModel : ViewModel() {

    // Сюда мы можем передавать экземпляры класса State и наследников
    private val _state = MutableLiveData<State>()

    val state: LiveData<State>
        get() = _state

    fun calculate(value: String?) {

        // Мы не создаем класс State, а создаем класс Progress() и не передаем параметры
        // Другими словами указываем, что сейчас состояние прогресса
        _state.value = Progress()

        if (value.isNullOrBlank()) {
            // Создаем состояние ошибки
            _state.value = Error()
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