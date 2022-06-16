package com.sambarnett.stockwatch.data.repository

import com.sambarnett.stockwatch.data.local.StockDao
import com.sambarnett.stockwatch.data.local.StockDatabase
import com.sambarnett.stockwatch.data.mapper.toCompanyListing
import com.sambarnett.stockwatch.data.network.StockAPI
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.StockRepository
import com.sambarnett.stockwatch.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import okio.IOException
import retrofit2.HttpException


class StockRepositoryImpl(
    val api: StockAPI,
    val database: StockDatabase
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


            val isDbEmpty = localListings.isEmpty() && query.isBlank()
            val loadFromCache = !isDbEmpty && !fetchFromRemote
            if(loadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }
            val remoteListings = try {
                val response = api.getListings()
                response.byteStream()

            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }

        }
    }
}