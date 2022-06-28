package com.sambarnett.stockwatch.di

import androidx.compose.runtime.withRunningRecomposer
import androidx.room.Room
import com.sambarnett.stockwatch.data.csv.CSVParser
import com.sambarnett.stockwatch.ui.companyListView.CompanyListingsViewModel
import com.sambarnett.stockwatch.data.csv.ListingsParser
import com.sambarnett.stockwatch.data.database.StockDatabase
import com.sambarnett.stockwatch.data.network.StockAPI
import com.sambarnett.stockwatch.data.repository.StockRepositoryImpl
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.squareup.moshi.Moshi
import okhttp3.Cache
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import java.io.File
import java.util.concurrent.TimeUnit



val apiModule = module {

    single {
        HttpLoggingInterceptor(
            HttpLoggingInterceptor.Logger { message ->
                HttpLoggingInterceptor.Logger.DEFAULT
            }).apply {
            level = HttpLoggingInterceptor.Level.NONE
        }

    }

    single<StockAPI> { get<Retrofit>().create(StockAPI::class.java) }

    single {
        Retrofit.Builder()
            .client(get())
            .baseUrl(StockAPI.BASE_URL)
            .addConverterFactory(MoshiConverterFactory.create())
            .build()
    }

    single {
        OkHttpClient.Builder()
            .addInterceptor(get<HttpLoggingInterceptor>())
            .build()
    }

    single {
        Moshi.Builder()
            .add(MoshiConverterFactory.create())
            .build()
    }

}


val dataBaseModule = module {
    single { get<StockDatabase>().stockDao }

    single {
        Room.databaseBuilder(
            androidApplication(),
            StockDatabase::class.java,
            "stock_database"
        )
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
