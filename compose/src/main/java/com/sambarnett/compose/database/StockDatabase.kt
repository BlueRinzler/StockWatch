package com.sambarnett.compose.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.sambarnett.compose.data.database.CompanyListingsEntity
import com.sambarnett.compose.database.StockDao

@Database(
    entities = [CompanyListingsEntity::class],
    version = 1, exportSchema = false
)
abstract class StockDatabase : RoomDatabase() {
    abstract val stockDao: StockDao
}


