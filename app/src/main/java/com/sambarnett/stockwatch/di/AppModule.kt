package com.sambarnett.stockwatch.di

import android.app.Application
import androidx.room.Room
import com.sambarnett.stockwatch.data.database.StockDatabase
import com.sambarnett.stockwatch.data.network.StockAPI
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl(StockAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

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
