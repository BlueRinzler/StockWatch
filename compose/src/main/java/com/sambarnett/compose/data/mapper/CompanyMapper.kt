package com.sambarnett.compose.data.mapper

import com.sambarnett.compose.data.database.CompanyListingsEntity
import com.sambarnett.compose.data.network.dto.CompanyDetailsDto
import com.sambarnett.compose.domain.model.CompanyDetails
import com.sambarnett.compose.domain.model.CompanyListing


/**
 * Extension mappers
 */
fun CompanyListingsEntity.toCompanyListing(): CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity(): CompanyListingsEntity {
    return CompanyListingsEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyDetailsDto.toCompanyDetails(): CompanyDetails {
    return CompanyDetails(
        symbol = symbol ?: "",
        name = name ?: "",
        description = description ?: "",
        country = country ?: "",
        sector = sector ?: "",
    )
}
