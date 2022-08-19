package com.sambarnett.stockwatch.data.network

import com.sambarnett.stockwatch.BuildConfig
import com.sambarnett.stockwatch.data.network.dto.CompanyDetailsDto
import retrofit2.http.Query
import okhttp3.ResponseBody
import retrofit2.http.GET


const val API_KEY = BuildConfig.API_KEY
const val BASE_URL = "https://www.alphavantage.co"


interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey: String = API_KEY): ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyDetails(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = API_KEY
    ): CompanyDetailsDto
}