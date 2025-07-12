package com.pm.airlineexplorer.airlineexplorerlist.ui

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Clear
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.pm.airlineexplorer.airlineexplorerlist.state.AirlineListStateHolder
import com.pm.airlineexplorer.airlineexplorerlist.state.AirlineViewModel
import com.pm.airlineexplorer.navigation.Screen
import com.pm.airlineexplorer.ui.theme.AirlineExplorerTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineScreen(
    viewModel: AirlineViewModel = hiltViewModel(),
    navController: NavController
) {
    val state = viewModel.state.collectAsState().value
    val searchQuery = remember { mutableStateOf("") }
    LaunchedEffect(Unit) {
        searchQuery.value = ""
        viewModel.onUiEvent(AirlineListStateHolder.UiEvent.ClearSearchQuery)
    }

    AirlineExplorerTheme {
        Scaffold(
            topBar = {
                TopAppBar(
                    title = {
                        TextField(
                            value = searchQuery.value,
                            onValueChange = {
                                searchQuery.value = it
                                viewModel.onUiEvent(
                                    AirlineListStateHolder.UiEvent.UpdateSearchQuery(
                                        it
                                    )
                                )
                            },
                            placeholder = { Text("Search by name or country") },
                            leadingIcon = {
                                Icon(
                                    Icons.Filled.Search,
                                    contentDescription = "Search"
                                )
                            },
                            trailingIcon = {
                                if (searchQuery.value.isNotEmpty()) {
                                    IconButton(onClick = {
                                        searchQuery.value = ""
                                        viewModel.onUiEvent(
                                            AirlineListStateHolder.UiEvent.UpdateSearchQuery(
                                                ""
                                            )
                                        )
                                    }) {
                                        Icon(Icons.Filled.Clear, contentDescription = "Clear")
                                    }
                                }
                            },
                            singleLine = true,
                            colors = TextFieldDefaults.textFieldColors(
                                containerColor = MaterialTheme.colorScheme.surface,
                                focusedIndicatorColor = MaterialTheme.colorScheme.primary,
                                unfocusedIndicatorColor = MaterialTheme.colorScheme.onSurface.copy(
                                    alpha = 0.5f
                                )
                            ),
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                )
            }
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 24.dp)
                    .fillMaxWidth()
            ) {
                when {
                    state.airlineUiState.isLoading -> {
                        Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                            CircularProgressIndicator()
                            //Text("Loading...")
                        }
                    }

                    else -> {
                        if (state.airlineUiState.airlineList.isEmpty() && !state.airlineUiState.isLoading) {
                            Text(
                                text = "No records available"
                            )
                        } else {
                            LazyColumn {
                                items(state.airlineUiState.airlineList) { airline ->
                                    Card(
                                        modifier = Modifier
                                            .fillMaxWidth()
                                            .padding(8.dp)
                                            .clickable {
                                                navController.navigate(
                                                    Screen.AirlineDetailScreen
                                                        .Param(airline.id)
                                                        .createRoute()
                                                )
                                            },
                                        elevation = CardDefaults.cardElevation(4.dp)
                                    ) {
                                        Row(modifier = Modifier.padding(16.dp)) {
                                            if (airline.logo_url.isNullOrBlank()) {
                                                Box(
                                                    modifier = Modifier
                                                        .size(64.dp)
                                                        .clip(RoundedCornerShape(8.dp)),
                                                    contentAlignment = Alignment.Center
                                                ) {
                                                    Text(
                                                        text = "No image",
                                                        style = MaterialTheme.typography.bodySmall
                                                    )
                                                }
                                            } else {
                                                AsyncImage(
                                                    model = airline.logo_url,
                                                    contentDescription = airline.name,
                                                    modifier = Modifier
                                                        .size(64.dp)
                                                        .clip(RoundedCornerShape(8.dp))
                                                )
                                            }
                                            Spacer(modifier = Modifier.width(16.dp))
                                            Column {
                                                Text(
                                                    text = airline.name,
                                                    style = MaterialTheme.typography.titleMedium
                                                )
                                                Text(
                                                    text = "Country: ${airline.country}",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                                Text(
                                                    text = "Fleet: ${airline.fleet_size}",
                                                    style = MaterialTheme.typography.bodySmall
                                                )
                                            }

                                            IconButton(onClick = {
                                                viewModel.onUiEvent(
                                                    AirlineListStateHolder.UiEvent.FavouriteItem(
                                                        airline.id
                                                    )
                                                )
                                            }) {
                                                Icon(
                                                    imageVector = if (airline.isFavourite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                                                    contentDescription = if (airline.isFavourite) "Unfavourite" else "Favourite"
                                                )
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
