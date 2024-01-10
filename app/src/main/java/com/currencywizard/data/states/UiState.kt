package com.currencywizard.data.states

sealed class UiState<out T> {
    class Success<T>(val value: T): UiState<T>()
    class Failure(val message: String): UiState<Nothing>()
    data object Loading: UiState<Nothing>()

    companion object {
        @JvmStatic
        fun <I> fromDataState(dataState: DataState<I>): UiState<I> =
            when(dataState) {
                is DataState.Success -> Success(dataState.value)
                is DataState.Failure -> Failure(dataState.message)
            }
    }
}