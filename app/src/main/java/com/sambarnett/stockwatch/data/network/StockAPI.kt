package com.sambarnett.stockwatch.data.network

import retrofit2.http.Query
import okhttp3.ResponseBody
import retrofit2.http.GET

interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = API_KEY
    ): ResponseBody

    companion object {
        const val API_KEY = "MB10MFAX9TKQ6H0N"
        const val BASE_URL = "https://www.alphavantage.co"
    }

}