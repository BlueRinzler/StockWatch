package com.sambarnett.compose.data

sealed class Resource<T: Any> {
    class Success<T : Any>(val data: T): Resource<T>()
    class Error<T: Any>(val message: String?): Resource<T>()
    class Exception<T: Any>(val e: Throwable): Resource<T>()
}