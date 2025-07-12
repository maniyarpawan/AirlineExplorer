package com.pm.airlineexplorer.details.ui

import android.content.Intent
import android.net.Uri
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.unit.dp
import androidx.core.content.ContextCompat
import androidx.hilt.navigation.compose.hiltViewModel
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.model.AirlineResponse
import com.pm.airlineexplorer.details.state.AirlineDetailsViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirlineDetailScreen(
    viewModel: AirlineDetailsViewModel = hiltViewModel()
) {
    val context = LocalContext.current
    val state = viewModel.state.collectAsState().value
    Scaffold(
        topBar = {
            TopAppBar(
                title = { state.airlineDetailsUiState.name?.let { Text(text = it) } }
            )
        }
    ) { padding ->
        Column (
            modifier = Modifier
                .padding(padding)
                .padding(16.dp)
                .fillMaxSize()
        ) {
            Text("Country: ${state.airlineDetailsUiState?.country}", style = MaterialTheme.typography.bodyLarge)
            Text("Headquarters: ${state.airlineDetailsUiState?.headquarters}", style = MaterialTheme.typography.bodyLarge)
            Text("Fleet size: ${state.airlineDetailsUiState?.fleet_size}", style = MaterialTheme.typography.bodyLarge)

            Spacer(modifier = Modifier.height(16.dp))

            state.airlineDetailsUiState?.website?.let { AnnotatedString(it) }?.let {
                ClickableText (
                    text = it,
                    style = MaterialTheme.typography.bodyLarge.copy(color = MaterialTheme.colorScheme.primary),
                    onClick = {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(state.airlineDetailsUiState?.website))
                        ContextCompat.startActivity(context, intent, null)
                    }
                )
            }
        }
    }
}
