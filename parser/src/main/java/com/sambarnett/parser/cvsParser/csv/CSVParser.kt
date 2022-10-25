package com.sambarnett.stockwatch.data.csv

import java.io.InputStream


/**
 * Interface for the Listings Parser. Takes type T generic for separation if CSV parser changes
 */
interface CSVParser<T> {
    suspend fun parse(stream: InputStream): List<T>
}