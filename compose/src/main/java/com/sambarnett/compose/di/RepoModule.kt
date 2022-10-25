package com.sambarnett.compose.di

import com.sambarnett.compose.csv.CSVParser
import com.sambarnett.compose.csv.ListingsParser
import com.sambarnett.stockwatch.data.repository.StockRepositoryImpl
import com.sambarnett.compose.domain.model.CompanyListing
import com.sambarnett.compose.domain.repository.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    @Singleton
    abstract fun bindListingParser(
        listingsParser: ListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindsStockRepo(
        stockRepositoryImpl: StockRepositoryImpl
    ): Repository
}

