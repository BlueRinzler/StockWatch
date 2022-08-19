package com.sambarnett.stockwatch.ui.stockview

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.stockwatch.data.Resource
import com.sambarnett.stockwatch.domain.model.CompanyDetails
import com.sambarnett.stockwatch.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class StockDetailsViewModel @Inject constructor(
    private val stockRepository: Repository,
    private val savedStateHandle: SavedStateHandle
) : ViewModel() {

    private val _uiState = MutableStateFlow(StockDetailsState())
    val uiState: StateFlow<StockDetailsState> = _uiState.asStateFlow()


    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            val companyDetailsResult =  stockRepository.getCompanyDetails(symbol)
            when (companyDetailsResult) {
                is Resource.Success -> {
                    companyDetailsResult.data.let { companyDetails ->
                        _uiState.update {
                            it.copy(stockDetails = companyDetails)
                        }
                    }
                }
                else -> Unit
            }
        }
    }
}



data class StockDetailsState(
    val stockDetails: CompanyDetails? = null,
    val isLoading: Boolean = false
)
