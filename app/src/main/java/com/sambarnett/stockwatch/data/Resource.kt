package com.sambarnett.stockwatch.data

sealed class Resource {
    class Success<T>(data: T?): Resource()
    class Error(message: String?): Resource()
    object Loading: Resource()
}