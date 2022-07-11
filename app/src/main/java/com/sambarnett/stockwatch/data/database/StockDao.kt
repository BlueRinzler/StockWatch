package com.sambarnett.stockwatch.data.database

import androidx.room.*
import kotlinx.coroutines.flow.Flow

/**
 * Room DAO.
 */
@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompanyListings(
        companyListingEntities: List<CompanyListingsEntity>
    )

    @Query("DELETE FROM companyListingsEntity")
    suspend fun clearCompanyListings()

    @Query(
        """
            SELECT * 
            FROM companyListingsEntity
            WHERE LOWER(stockName) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbolName
        """
    )
    suspend fun searchCompanyListing(query: String): List<CompanyListingsEntity>


}
