package com.sambarnett.stockwatch.data.repository

import com.sambarnett.stockwatch.data.csv.CSVParser
import com.sambarnett.stockwatch.data.database.StockDatabase
import com.sambarnett.stockwatch.data.mapper.toCompanyListing
import com.sambarnett.stockwatch.data.mapper.toCompanyListingEntity
import com.sambarnett.stockwatch.data.network.StockAPI
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.StockRepository
import com.sambarnett.stockwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockAPI,
    private val database: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>
) : StockRepository {

    private val stockDao = database.stockDao

    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {
        return flow {
            emit(Resource.Loading(true))
            val localListings = stockDao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListings.map { it.toCompanyListing() }
            ))
            //check if DB is empty and if query is blank return true
            val isDbEmpty: Boolean = localListings.isEmpty() && query.isBlank()
            //check if both false, if true then load from cache and end function return@flow
            val loadFromCache : Boolean = !isDbEmpty && !fetchFromRemote
            if (loadFromCache) {
                emit(Resource.Loading(false))
               return@flow
            }
            //Accept listings from API and parse, if no connection, emit error and return null
            val remoteListings = try {
                val response = api.getListings()
                companyListingParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }
            //If remote listings are not null -> clear the cache and insert new listings
            remoteListings?.let { listings ->
                stockDao.clearCompanyListings()
                stockDao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )
                emit(Resource.Success(
                    data = stockDao
                        .searchCompanyListing("")
                        .map { it.toCompanyListing() }
                ))
                emit(Resource.Loading(false))
            }
        }
    }
}

