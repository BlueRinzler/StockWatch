package com.sambarnett.compose.navigation

import androidx.compose.runtime.Composable
import com.sambarnett.compose.core_ui.views.StockInfoScreen.StockInfoScreen
import com.sambarnett.compose.core_ui.views.StockListScreen.StockListScreen

interface StockDestination {
    val route: String
}

object CompanyList : StockDestination {
    override val route: String = "companyList"
}

object CompanyInfo : StockDestination {
    override val route: String = "companyInfo"
}

