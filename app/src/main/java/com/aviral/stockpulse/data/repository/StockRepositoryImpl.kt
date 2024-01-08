package com.aviral.stockpulse.data.repository

import com.aviral.stockpulse.data.csv.CSVParser
import com.aviral.stockpulse.data.csv.CompanyListingsParser
import com.aviral.stockpulse.data.local.StockDatabase
import com.aviral.stockpulse.data.mapper.toCompanyListing
import com.aviral.stockpulse.data.mapper.toCompanyListingEntity
import com.aviral.stockpulse.data.remote.StockApi
import com.aviral.stockpulse.domain.model.CompanyListing
import com.aviral.stockpulse.domain.repository.StockRepository
import com.aviral.stockpulse.util.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class StockRepositoryImpl @Inject constructor(
    private val api: StockApi,
    private val db: StockDatabase,
    private val companyListingsParser: CSVParser<CompanyListing>
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
                companyListingsParser.parse(response.byteStream())
            } catch (e: IOException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            } catch (e: HttpException) {
                e.printStackTrace()
                emit(Resource.Error("Couldn't load data"))
                null
            }

            remoteListing?.let { listings ->
                dao.clearCompanyListing()
                dao.insertCompanyListings(
                    listings.map { it.toCompanyListingEntity() }
                )

                emit(Resource.Success(data = listings))
                emit(Resource.Loading(isLoading = false))
            }
        }

    }


}