package com.sambarnett.stockwatch.data.network.dto

import com.squareup.moshi.Json


//Image resource
data class CompanyDetailsDto(
    @field:Json(name = "Symbol") val symbol: String?,
    @field:Json(name ="Name") val name: String?,
    @field:Json(name = "Description") val description: String?,
    @field:Json(name = "Country") val country: String?,
    @field:Json(name = "Sector") val sector: String?,
)
