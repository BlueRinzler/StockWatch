package com.sambarnett.stockwatch.ui.companyListView


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.StockRepository
import com.sambarnett.stockwatch.util.Resource
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch

class CompanyListingsViewModel(private val stockRepository: StockRepository) : ViewModel() {

    private val _uiState = MutableStateFlow(CompanyListingsState())
    val uiState: StateFlow<CompanyListingsState> = _uiState.asStateFlow()

    private var fetchJob: Job? = null

    init {
        getCompanyListings()
    }


    fun onEvent(event: CompanyListingsEvent) {
        when (event) {
            is CompanyListingsEvent.Refresh -> {
                getCompanyListings(fetchFromRemote = true)
            }
            is CompanyListingsEvent.OnSearchQueryChange -> {
                _uiState.value.copy(searchQuery = event.query)
                fetchJob?.cancel()
                fetchJob = viewModelScope.launch {
                    delay(500L)
                    getCompanyListings()
                }
            }
        }
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
    val isSuccess: Boolean = false,
    val isRefresh: Boolean = false,
    val searchQuery: String = ""
)

sealed class CompanyListingsEvent {
    object Refresh : CompanyListingsEvent()
    data class OnSearchQueryChange(val query: String) : CompanyListingsEvent()
}