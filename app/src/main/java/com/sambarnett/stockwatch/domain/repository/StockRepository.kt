package com.sambarnett.stockwatch.domain.repository

import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>


}