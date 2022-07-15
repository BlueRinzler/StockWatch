package com.sambarnett.stockwatch.domain.repository

interface Repository<T> {

    fun getData(): List<T>
}