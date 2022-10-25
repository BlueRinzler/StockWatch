package com.sambarnett.stockwatch.ui.companyListView


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.Repository
import com.sambarnett.stockwatch.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val stockRepository: Repository) :
    ViewModel() {

    private val _uiState = MutableStateFlow(CompanyListingsState())
    val uiState: StateFlow<CompanyListingsState> = _uiState.asStateFlow()
    private var searchStock: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.OnSearchQueryChange -> {
                val searchQuery = event.query.lowercase()
                _uiState.value = CompanyListingsState(searchQuery = searchQuery)
                searchStock?.cancel()
                searchStock = viewModelScope.launch {
                    delay(500)
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
            stockRepository.getCompanyListingsQuery(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { listings ->
                                _uiState.update {
                                    it.copy(companies = listings)
                                }
                            }
                        }
                        is Resource.Error -> Unit
                        is Resource.Exception -> Unit
                    }
                }
        }
    }
}


data class CompanyListingsState(
    val companies: List<CompanyListing> = emptyList(),
    val searchQuery: String = ""
)

sealed class CompanyListingEvent {
    data class OnSearchQueryChange(val query: String) : CompanyListingEvent()
}



