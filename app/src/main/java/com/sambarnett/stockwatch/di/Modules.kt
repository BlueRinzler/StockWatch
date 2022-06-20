package com.sambarnett.stockwatch.di

import androidx.room.Room
import androidx.room.RoomDatabase
import com.sambarnett.stockwatch.companyListView.CompanyListingsViewModel
import com.sambarnett.stockwatch.data.csv.CSVParser
import com.sambarnett.stockwatch.data.csv.ListingsParser
import com.sambarnett.stockwatch.data.local.StockDatabase
import com.sambarnett.stockwatch.data.network.StockAPI
import com.sambarnett.stockwatch.data.repository.StockRepositoryImpl
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import kotlin.math.sin


val apiModule = module {

    single {
        Retrofit.Builder()
            .baseUrl(StockAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

}

val dataBaseModule = module {
    single { get<StockDatabase>().stockDao }

    single {
        Room.databaseBuilder(
            androidApplication(),
            StockDatabase::class.java,
            "stock_database")
            .fallbackToDestructiveMigration()
            .build()
    }
}

val csvParser = module {
    single { ListingsParser() }
}
val repoModule = module {
    single { StockRepositoryImpl(get(), get(), get()) }

}

val viewModels = module {

    viewModel { CompanyListingsViewModel(get()) }
}
