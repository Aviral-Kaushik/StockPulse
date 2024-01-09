package com.aviral.stockpulse.data.mapper

import com.aviral.stockpulse.data.local.CompanyListingEntity
import com.aviral.stockpulse.data.remote.dto.CompanyInfoDto
import com.aviral.stockpulse.domain.model.CompanyInfo
import com.aviral.stockpulse.domain.model.CompanyListing

fun CompanyListingEntity.toCompanyListing() : CompanyListing {
    return CompanyListing(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyListing.toCompanyListingEntity() : CompanyListingEntity {
    return CompanyListingEntity(
        name = name,
        symbol = symbol,
        exchange = exchange
    )
}

fun CompanyInfoDto.toCompanyInfo() : CompanyInfo {
    return CompanyInfo(
        symbol = symbol ?: "",
        description = description ?: "",
        name = name ?: "",
        country = country ?: "",
        industry = industry ?: ""
    )
}