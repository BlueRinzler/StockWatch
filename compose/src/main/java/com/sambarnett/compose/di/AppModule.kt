package com.sambarnett.compose.di

import android.app.Application
import androidx.room.Room
import com.sambarnett.compose.database.StockDatabase
import com.sambarnett.compose.data.network.BASE_URL
import com.sambarnett.compose.data.network.StockAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder().build()
        )
        .build()

    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase = Room.databaseBuilder(
        app, StockDatabase::class.java,
        "stock_database"
    ).fallbackToDestructiveMigration()
        .build()

    @Provides
    @Singleton
    fun provideCompanyAPI(retrofit: Retrofit): StockAPI =
        retrofit.create(StockAPI::class.java)
}
