package com.sambarnett.compose.core_ui.views.StockInfoScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.sambarnett.compose.data.Resource
import com.sambarnett.compose.domain.repository.Repository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.async
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class StockInfoViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: Repository
) : ViewModel() {

    var state by mutableStateOf(StockInfoState())
        private set

    init {
        viewModelScope.launch {
            val symbol = savedStateHandle.get<String>("symbol") ?: return@launch
            val companyInfoResult = repository.getCompanyDetails(symbol)

            when (companyInfoResult) {
                is Resource.Success -> {
                    state = state.copy(
                        company = companyInfoResult.data
                    )
                }

                is Resource.Error -> Unit
                is Resource.Exception -> Unit
            }
        }
    }
}