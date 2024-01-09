package com.aviral.stockpulse.di

import com.aviral.stockpulse.data.csv.CSVParser
import com.aviral.stockpulse.data.csv.CompanyListingsParser
import com.aviral.stockpulse.data.csv.IntradayInfoParser
import com.aviral.stockpulse.data.repository.StockRepositoryImpl
import com.aviral.stockpulse.domain.model.CompanyListing
import com.aviral.stockpulse.domain.model.IntradayInfo
import com.aviral.stockpulse.domain.repository.StockRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    @Singleton
    abstract fun bindCompanyListingsParser(
        companyListingsParser: CompanyListingsParser
    ): CSVParser<CompanyListing>

    @Binds
    @Singleton
    abstract fun bindIntradayInfoParser(
        intradayInfoParser: IntradayInfoParser
    ): CSVParser<IntradayInfo>


    @Binds
    @Singleton
    abstract fun bindStockRepository(
        stockRepositoryImpl: StockRepositoryImpl
    ): StockRepository
}