package com.aviral.stockpulse.data.remote

import com.aviral.stockpulse.common.Objects
import okhttp3.ResponseBody
import retrofit2.http.GET
import retrofit2.http.Query

interface StockApi {

    @GET("query?function=LISTING_STATUS")
    suspend fun getListings(
        @Query("apikey") apiKey: String = Objects.API_KEY
    ) : ResponseBody

}