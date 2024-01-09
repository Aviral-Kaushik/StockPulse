package com.aviral.stockpulse.data.remote

import com.aviral.stockpulse.common.Objects
import com.aviral.stockpulse.data.remote.dto.CompanyInfoDto
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = Objects.API_KEY
    ) : ResponseBody

    @GET("query?function=TIME_SERIES_INTRADAY&interval=60min&datatype=csv")
    suspend fun getIntradayInfo(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = Objects.API_KEY
    ) : ResponseBody

    @GET("query?function=OVERVIEW")
    suspend fun getCompanyResponse(
        @Query("symbol") symbol: String,
        @Query("apikey") apiKey: String = Objects.API_KEY
    ) : CompanyInfoDto

}