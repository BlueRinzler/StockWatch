package com.sambarnett.stockwatch.ui.companyListView


import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.stockwatch.domain.model.CompanyListing
import com.sambarnett.stockwatch.domain.repository.Repository
import com.sambarnett.stockwatch.data.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CompanyListingsViewModel @Inject constructor(private val stockRepository: Repository) :
    ViewModel() {


    private val _uiState = MutableStateFlow(CompanyListingsState())
    val uiState: StateFlow<CompanyListingsState> = _uiState.asStateFlow()


    init {
        getCompanyListings()
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
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                        is Resource.Error<*> -> Unit
                    }
                }
        }
    }

    fun getCompanyListingsSearch(
        query: String = _uiState.value.searchQuery.lowercase(),
        fetchFromRemote: Boolean = false
    ) : Flow<List<CompanyListing>> = flow {
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
                        is Resource.Loading -> {
                            _uiState.update {
                                it.copy(isLoading = true)
                            }
                        }
                        is Resource.Error<*> -> Unit
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



