package com.sambarnett.stockwatch.data.csv

import com.opencsv.CSVReader
import com.sambarnett.stockwatch.domain.model.CompanyListing
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.IOException
import java.io.InputStream
import java.io.InputStreamReader
import javax.inject.Inject
import javax.inject.Singleton
import kotlin.jvm.Throws

/**
 * Implements for the CSV Parser. Parses out symbol, name and exchange from csv file
 */
@Singleton
class ListingsParser @Inject constructor(
    private val csvReader: CSVReader
) : CSVParser<CompanyListing> {

    override suspend fun parse(stream: InputStream): List<CompanyListing> =
        withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val symbol = line.getOrNull(0)
                    val name = line.getOrNull(1)
                    val exchange = line.getOrNull(2)
                    CompanyListing(
                        name = name ?: return@mapNotNull null,
                        symbol = symbol ?: return@mapNotNull null,
                        exchange = exchange ?: return@mapNotNull null
                    )
                }
                .also {
                    csvReader.close()
                }
        }
}