package com.sambarnett.stockwatch.di

import com.sambarnett.stockwatch.data.csv.CSVParser
import com.sambarnett.stockwatch.data.csv.ListingsParser
import com.sambarnett.stockwatch.data.repository.StockRepositoryImpl
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.Repository
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

