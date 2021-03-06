package com.sambarnett.stockwatch.ui.companyListView


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.StockRepository
import com.sambarnett.stockwatch.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val stockRepository: StockRepository) :
    ViewModel() {


    private val _uiState = MutableStateFlow(CompanyListingsState())
    val uiState: StateFlow<CompanyListingsState> = _uiState.asStateFlow()


    init {
        getCompanyListings()
    }


    fun getListings(query: String) {
        getCompanyListings(query)
    }


    private fun getCompanyListings(
        query: String = _uiState.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            stockRepository.getCompanyListings(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data?.let { listings ->
                                _uiState.update {
                                    it.copy(companies = listings)
                                }
                            }
                        }
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = result.isLoading)
                            }
                        }
                        is Resource.Error -> Unit
                    }
                }
        }
    }


}


data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val isLoading: Boolean = false,
    val searchQuery: String = ""
)



