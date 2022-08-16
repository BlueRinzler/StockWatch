package com.sambarnett.stockwatch.data

sealed class Resource<out T: Any> {
    class Success<T : Any>(val data: T): Resource<T>()
    class Error<T>(val message: String): Resource<Nothing>()
    object Loading: Resource<Nothing>()
}