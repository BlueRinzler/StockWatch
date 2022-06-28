package com.sambarnett.stockwatch.data.mapper

import com.sambarnett.stockwatch.data.database.CompanyListingsEntity
import com.sambarnett.stockwatch.domain.model.CompanyListing


/**
 * Interface for the Listings Parser. Takes type T generic for separation if CSV parser changes
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