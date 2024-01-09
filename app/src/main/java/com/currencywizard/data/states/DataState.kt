package com.currencywizard.data.states

sealed class DataState<out T> {
    class Success<T>(val value: T): DataState<T>()
    class Failure(val message: String): DataState<Nothing>()
}
