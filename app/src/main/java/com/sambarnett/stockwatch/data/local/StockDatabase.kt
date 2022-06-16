package com.sambarnett.stockwatch.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [CompanyListingsEntity::class],
    version = 1
)
abstract class StockDatabase : RoomDatabase() {
    abstract val stockDao: StockDao
}


