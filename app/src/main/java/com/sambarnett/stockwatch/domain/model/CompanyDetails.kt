package com.sambarnett.stockwatch.domain.model

import com.squareup.moshi.Json

data class CompanyDetails(
    val symbol: String,
    val name: String,
    val description: String,
    val country: String,
    val sector: String,
)

