package com.sambarnett.stockwatch.data.mapper

import com.sambarnett.stockwatch.data.database.CompanyListingsEntity
import com.sambarnett.stockwatch.domain.model.CompanyListing


/**
 * Mapper to turn Entity to Listing and vice versa
 * Use for seperation purposes
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