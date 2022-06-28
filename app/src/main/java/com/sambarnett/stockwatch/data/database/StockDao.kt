package com.sambarnett.stockwatch.data.database

import androidx.room.*

/**
 * Room DAO.
 */
@Dao
interface StockDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertCompanyListings(
        companyListingEntities: List<CompanyListingsEntity>
    )

    @Query("DELETE FROM companyListingsEntity")
    fun clearCompanyListings()

    @Query(
        """
            SELECT * 
            FROM companyListingsEntity
            WHERE LOWER(stockName) LIKE '%' || LOWER(:query) || '%' OR
                UPPER(:query) == symbolName
        """
    )
    fun searchCompanyListing(query: String): List<CompanyListingsEntity>


}
