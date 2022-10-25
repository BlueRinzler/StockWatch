package com.sambarnett.stockwatch.data.repository

import com.sambarnett.stockwatch.data.csv.CSVParser
import com.sambarnett.stockwatch.data.database.StockDatabase
import com.sambarnett.stockwatch.data.mapper.toCompanyListing
import com.sambarnett.stockwatch.data.mapper.toCompanyListingEntity
import com.sambarnett.stockwatch.data.network.StockAPI
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.Repository
import com.sambarnett.stockwatch.data.Resource
import com.sambarnett.stockwatch.data.mapper.toCompanyDetails
import com.sambarnett.stockwatch.domain.model.CompanyDetails
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockAPI,
    private val database: StockDatabase,
    private val companyListingParser: CSVParser<CompanyListing>,
) : Repository {

    private val stockDao = database.stockDao


    override suspend fun getCompanyListingsQuery(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> = flow {
        val localListings = stockDao.searchCompanyListing(query)
        emit(Resource.Success(
            data = localListings.map { it.toCompanyListing() }
        ))
        //check if DB is empty and if query is blank return true
        val isDbEmpty: Boolean = localListings.isEmpty() && query.isBlank()
        //check if both false, if true then load from cache and end function return@flow
        val loadFromCache: Boolean = !isDbEmpty && !fetchFromRemote
        if (loadFromCache) {
            return@flow
        }
        //Accept listings from API and parse, if no connection, emit error and return null
        val remoteListings = try {
            val response = api.getListings()
            companyListingParser.parse(response.byteStream())
        } catch (e: HttpException) {
            emit(Resource.Error(message = e.toString()))
            null
        } catch (e: Throwable) {
            emit(Resource.Exception(e))
            null
        }

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
        }
    }

    override suspend fun getCompanyDetails(symbol: String): Resource<CompanyDetails> {
        return try {
            val result = api.getCompanyDetails(symbol)
            Resource.Success(data = result.toCompanyDetails())
        } catch (e: HttpException) {
            Resource.Error(message = e.toString())
        } catch (e: Exception) {
            Resource.Exception(e)
        }
    }
}




