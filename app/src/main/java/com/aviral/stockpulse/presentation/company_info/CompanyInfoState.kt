package com.aviral.stockpulse.presentation.company_info

import com.aviral.stockpulse.domain.model.CompanyInfo
import com.aviral.stockpulse.domain.model.IntradayInfo

data class CompanyInfoState(
    val stockInfo: List<IntradayInfo> = emptyList(),
    val company: CompanyInfo? = null,
    val isLoading: Boolean = false,
    val error: String? = null
)
