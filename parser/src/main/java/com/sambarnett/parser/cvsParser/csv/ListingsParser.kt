package com.sambarnett.parser.cvsParser.csv

import com.opencsv.CSVReader
import com.sambarnett.stockwatch.data.csv.CSVParser

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader

/**
 * Implements for the CSV Parser. Parses out symbol, name and exchange from csv file
 */
//class ListingsParser @Inject constructor() :
//    CSVParser<T> {
//
//    override suspend fun parse(stream: InputStream): List<T> {
//        val csvReader = CSVReader(InputStreamReader(stream))
//        return withContext(Dispatchers.IO) {
//            csvReader
//                .readAll()
//                .drop(1)
//                .mapNotNull { line ->
//                    val symbol = line.getOrNull(0)
//                    val name = line.getOrNull(1)
//                    val exchange = line.getOrNull(2)
//                    CompanyListing(
//                        name = name ?: return@mapNotNull null,
//                        symbol = symbol ?: return@mapNotNull null,
//                        exchange = exchange ?: return@mapNotNull null
//                    )
//                }
//                .also {
//                    csvReader.close()
//                }
//        }
//    }
//}