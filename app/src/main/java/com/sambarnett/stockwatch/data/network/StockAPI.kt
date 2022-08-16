package com.sambarnett.stockwatch.data.network

import android.app.Application
import android.content.pm.ApplicationInfo
import androidx.compose.ui.input.key.Key
import com.sambarnett.stockwatch.BuildConfig
import com.sambarnett.stockwatch.data.database.CompanyListingsEntity
import com.sambarnett.stockwatch.domain.model.CompanyListing
import okhttp3.Response
import retrofit2.http.Query
import okhttp3.ResponseBody
import retrofit2.http.GET


const val API_KEY = BuildConfig.API_KEY
const val BASE_URL = "https://www.alphavantage.co"


interface StockAPI {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(@Query("apikey") apiKey: String = API_KEY): ResponseBody

}