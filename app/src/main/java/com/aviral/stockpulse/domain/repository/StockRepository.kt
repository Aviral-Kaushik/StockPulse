package com.aviral.stockpulse.domain.repository

import com.aviral.stockpulse.domain.model.CompanyListing
import com.aviral.stockpulse.util.Resource
import kotlinx.coroutines.flow.Flow

interface StockRepository {

    suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ) : Flow<Resource<List<CompanyListing>>>

}