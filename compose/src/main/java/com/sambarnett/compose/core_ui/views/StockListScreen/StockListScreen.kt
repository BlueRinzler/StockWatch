package com.sambarnett.compose.core_ui.views.StockListScreen

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun StockListScreen(
    modifier: Modifier = Modifier,
    viewModel: StockListingsViewModel = hiltViewModel()
) {
    val state = viewModel.uiState
    Column(modifier = modifier.fillMaxSize()) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(CompanyListingEvent.OnSearchQueryChange(it))
            },
            modifier = modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search")
            },
            maxLines = 1,
            singleLine = true
        )
        LazyColumn(
            modifier = modifier.fillMaxSize(),
        ) {
            items(state.companies) {
                CompanyItem(
                    company = it,
                    onClick = { it -> it },
                    modifier = modifier
                        .fillMaxWidth()
                        .padding(16.dp)
                )
            }
        }
    }
}