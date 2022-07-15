package com.sambarnett.stockwatch.data.network

import android.app.Application
import androidx.room.Room
import com.sambarnett.stockwatch.data.database.StockDatabase
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
    fun provideRetrofit(okHttpClient: OkHttpClient, ): Retrofit = Retrofit.Builder()
        .baseUrl(StockAPI.BASE_URL)
        .addConverterFactory(MoshiConverterFactory.create())
        .client(
            OkHttpClient.Builder().build()
        )
        .build()



    @Provides
    @Singleton
    fun provideStockDatabase(app: Application): StockDatabase {
        return Room.databaseBuilder(
            app, StockDatabase::class.java,
            "stock_database"
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Provides
    @Singleton
    fun provideCompanyAPI(retrofit: Retrofit): StockAPI =
        retrofit.create(StockAPI::class.java)

}
