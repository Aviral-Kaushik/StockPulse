package com.aviral.stockpulse.data.csv

import android.os.Build
import androidx.annotation.RequiresApi
import com.aviral.stockpulse.data.mapper.toIntradayInfo
import com.aviral.stockpulse.data.remote.dto.IntradayInfoDto
import com.aviral.stockpulse.domain.model.IntradayInfo
import com.opencsv.CSVReader
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.InputStream
import java.io.InputStreamReader
import java.time.LocalDate
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class IntradayInfoParser @Inject constructor() : CSVParser<IntradayInfo> {

    @RequiresApi(Build.VERSION_CODES.O)
    override suspend fun parse(stream: InputStream): List<IntradayInfo> {
        val csvReader = CSVReader(InputStreamReader(stream))

        return withContext(Dispatchers.IO) {
            csvReader
                .readAll()
                .drop(1)
                .mapNotNull { line ->
                    val timestamp = line.getOrNull(0) ?: return@mapNotNull null
                    val close = line.getOrNull(1) ?: return@mapNotNull null
                    val dto = IntradayInfoDto(
                        timestamp = timestamp,
                        close = close.toDouble()
                    )
                    dto.toIntradayInfo()
                }
                .filter {
                    it.date.dayOfMonth == LocalDate.now().minusDays(1).dayOfMonth
                }
                .sortedBy {
                    it.date.hour
                }
                .also {
                    csvReader.close()
                }

        }
    }

}