package com.sambarnett.stockwatch.data.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey


/**
 * Room entity. Holds name, symbol and exchange.
 */
@Entity(tableName = "companyListingsEntity")
data class CompanyListingsEntity(
    @PrimaryKey
    val id: Int? = null,
    @ColumnInfo(name = "stockName")
    val name: String,
    @ColumnInfo(name = "symbolName")
    val symbol: String,
    @ColumnInfo(name = "exchangeName")
    val exchange: String

)
