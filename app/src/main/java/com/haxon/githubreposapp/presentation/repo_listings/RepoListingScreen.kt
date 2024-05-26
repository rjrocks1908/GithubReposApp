package com.haxon.githubreposapp.presentation.repo_listings

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.haxon.githubreposapp.data.mapper.toRepoListing

@Composable
fun RepoListingScreen(
    viewModel: RepoListingViewModel = hiltViewModel()
) {
    val state = viewModel.state
    val response = viewModel.repoResponse.collectAsLazyPagingItems()

    LaunchedEffect(key1 = response) {
        if (response.itemCount >= 15) {
            viewModel.onEvent(
                RepoListingEvent.CacheData(
                    response.itemSnapshotList.items.take(15).map { it.toRepoListing() }
                )
            )
        } else {
            viewModel.onEvent(
                RepoListingEvent.CacheData(
                    response.itemSnapshotList.items.map { it.toRepoListing() }
                )
            )
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        OutlinedTextField(
            value = state.searchQuery,
            onValueChange = {
                viewModel.onEvent(RepoListingEvent.OnSearchQueryChanged(it))
            },
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            placeholder = {
                Text(text = "Search...")
            },
            maxLines = 1,
            singleLine = true
        )

        if (state.isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            LazyColumn(
                modifier = Modifier.fillMaxSize()
            ) {
                items(response.itemCount) { i ->
                    val repo = response[i]
                    repo?.toRepoListing()?.let {
                        RepoItem(
                            repo = it,
                            modifier = Modifier
                                .fillMaxSize()
                                .clickable {

                                }
                                .padding(16.dp)
                        )
                    }
                    if (i < response.itemCount) {
                        Divider(
                            modifier = Modifier.padding(
                                horizontal = 16.dp
                            )
                        )
                    }
                }

                when (val loadState = response.loadState.append) {
                    is LoadState.Error -> {
                        item {
                            ErrorItem(
                                errorMessage = loadState.error.localizedMessage,
                                onRetryClick = { response.retry() })
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    is LoadState.NotLoading -> {}
                }

                when (val loadState = response.loadState.refresh) {
                    is LoadState.Error -> {
                        item {
                            ErrorItem(
                                errorMessage = loadState.error.localizedMessage,
                                onRetryClick = { response.retry() })
                        }
                    }

                    is LoadState.Loading -> {
                        item {
                            CircularProgressIndicator()
                        }
                    }

                    is LoadState.NotLoading -> {}
                }
            }
        }
    }
}

@Composable
fun ErrorItem(errorMessage: String?, onRetryClick: () -> Unit) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
        modifier = Modifier.fillMaxWidth()
    ) {
        Text(text = errorMessage ?: "Unknown Error", color = Color.Red)
        Spacer(modifier = Modifier.height(8.dp))
        Button(onClick = onRetryClick) {
            Text("Retry")
        }
    }
}