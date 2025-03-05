package com.example.coroutinefactorialapp

sealed class State()

// Поскольку они не содержат никаких полей, то мы можем объявить их Object классами
data object Error : State()
data object Progress : State()

class Result(
    val factorial: String,
) : State()