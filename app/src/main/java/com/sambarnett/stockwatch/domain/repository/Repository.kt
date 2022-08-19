package com.sambarnett.stockwatch.domain.repository

import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.data.Resource
import com.sambarnett.stockwatch.domain.model.CompanyDetails
import kotlinx.coroutines.flow.Flow

interface Repository {

    suspend fun getCompanyListingsQuery(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>>

    suspend fun getCompanyDetails(
        symbol: String
    ) : Resource<CompanyDetails>
}