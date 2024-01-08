package com.aviral.stockpulse.data.repository

import com.aviral.stockpulse.data.local.StockDatabase
import com.aviral.stockpulse.data.mapper.toCompanyListing
import com.aviral.stockpulse.data.remote.StockApi
import com.aviral.stockpulse.domain.model.CompanyListing
import com.aviral.stockpulse.domain.repository.StockRepository
import com.aviral.stockpulse.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException

class StockRepositoryImpl(
    val api: StockApi,
    val db: StockDatabase
) : StockRepository {

    private val dao = db.dao
    override suspend fun getCompanyListings(
        fetchFromRemote: Boolean,
        query: String
    ): Flow<Resource<List<CompanyListing>>> {

        return flow {
            emit(Resource.Loading(true))
            val localListing = dao.searchCompanyListing(query)
            emit(Resource.Success(
                data = localListing.map { it.toCompanyListing() }
            ))

            val isLocalDbEmpty = localListing.isEmpty() && query.isBlank()
            val shouldJustLoadFromCache = !isLocalDbEmpty && !fetchFromRemote

            if (shouldJustLoadFromCache) {
                emit(Resource.Loading(false))
                return@flow
            }

            val remoteListing = try {
                val response = api.getListings()
                response.byteStream()
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
            }
        }

    }


}