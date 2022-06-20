package com.sambarnett.stockwatch.data.local

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


@Entity(tableName = "companyListingsEntity")
data class CompanyListingsEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Int? = 0,
    @ColumnInfo(name = "stockName")
    val name: String,
    @ColumnInfo(name = "symbolName")
    val symbol: String,
    @ColumnInfo(name = "exchangeName")
    val exchange: String,
)
