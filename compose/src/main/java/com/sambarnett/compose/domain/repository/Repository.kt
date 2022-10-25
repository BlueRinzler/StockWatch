package com.sambarnett.compose.domain.repository

import com.sambarnett.compose.domain.model.CompanyListing
import com.sambarnett.compose.data.Resource
import com.sambarnett.compose.domain.model.CompanyDetails
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