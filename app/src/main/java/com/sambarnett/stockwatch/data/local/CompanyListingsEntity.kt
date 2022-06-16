package com.sambarnett.stockwatch.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "companyListingsEntity")
data class CompanyListingsEntity(
    val name: String,
    val symbol: String,
    val exchange: String,
    @PrimaryKey
    val id: Int? = null
)
