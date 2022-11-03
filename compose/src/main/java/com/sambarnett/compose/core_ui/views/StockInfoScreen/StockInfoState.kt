package com.sambarnett.compose.core_ui.views.StockInfoScreen

import com.sambarnett.compose.domain.model.CompanyDetails

data class StockInfoState(
    val company: CompanyDetails? = null,
    val error: String? = null
)