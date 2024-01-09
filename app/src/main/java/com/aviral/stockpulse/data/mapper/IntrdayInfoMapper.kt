package com.aviral.stockpulse.data.mapper

import android.os.Build
import android.util.Log
import androidx.annotation.RequiresApi
import com.aviral.stockpulse.data.remote.dto.IntradayInfoDto
import com.aviral.stockpulse.domain.model.IntradayInfo
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

@RequiresApi(Build.VERSION_CODES.O)
fun IntradayInfoDto.toIntradayInfo(): IntradayInfo {
    val pattern = "yyyy-MM-dd HH:mm:ss"

    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
        val formatter = DateTimeFormatter.ofPattern(pattern, Locale.getDefault());
        val localDateTime = LocalDateTime.parse(timestamp, formatter)
        return IntradayInfo(
            localDateTime,
            close
        )
    } else {
        Log.d("AviralStockPulse", "toIntradayInfo: Cannot parse date and time as android level is lesser than 0")
    }

    return IntradayInfo(
        LocalDateTime.now(),
        0.0
    )

}