package com.sambarnett.stockwatch.domain.model


data class CompanyDetails(
    val symbol: String,
    val name: String,
    val description: String,
    val country: String,
    val sector: String,
)

