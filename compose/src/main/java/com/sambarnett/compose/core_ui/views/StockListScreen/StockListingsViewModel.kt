package com.sambarnett.compose.core_ui.views.StockListScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.compose.data.Resource
import com.sambarnett.compose.domain.model.CompanyListing
import com.sambarnett.compose.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.StateFlow

import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockListingsViewModel @Inject constructor(private val stockRepository: Repository) :
    ViewModel() {

    var uiState by mutableStateOf(CompanyListingsState())
        private set

    private var searchStock: Job? = null

    init {
        getCompanyListings()
    }

    fun onEvent(event: CompanyListingEvent) {
        when (event) {
            is CompanyListingEvent.OnSearchQueryChange -> {
                val searchQuery = event.query.lowercase()
                uiState = uiState.copy(searchQuery = searchQuery)
                searchStock?.cancel()
                searchStock = viewModelScope.launch {
                    delay(500)
                    getCompanyListings()
                }
            }
        }
    }

    private fun getCompanyListings(
        query: String = uiState.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) {
        viewModelScope.launch {
            stockRepository.getCompanyListingsQuery(fetchFromRemote, query)
                .collect { result ->
                    when (result) {
                        is Resource.Success -> {
                            result.data.let { listings ->
                                uiState = uiState.copy(
                                    companies = listings
                                )
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
