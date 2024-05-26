package com.haxon.githubreposapp.presentation.repo_info

import android.widget.Toast
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.haxon.githubreposapp.R
import com.haxon.githubreposapp.domain.model.Contributor
import com.haxon.githubreposapp.domain.model.RepoListing

@Composable
fun RepoInfoScreen(
    repo: RepoListing,
    modifier: Modifier = Modifier,
    viewModel: RepoInfoViewModel = hiltViewModel(),
    onProjectLinkClick: (String) -> Unit
) {
    val context = LocalContext.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(if (repo.ownerImageLink.isNullOrEmpty()) "-" else repo.ownerImageLink)
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(text = repo.repoName, style = MaterialTheme.typography.titleSmall)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "Owner: ${repo.ownerName}", style = MaterialTheme.typography.bodyLarge)
        Spacer(modifier = Modifier.height(8.dp))
        Text(
            text = if (repo.description.isNullOrEmpty()) "Description not available" else repo.description,
            style = MaterialTheme.typography.bodyMedium
        )
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!repo.projectLink.isNullOrEmpty())
                    onProjectLinkClick(repo.projectLink)
                else {
                    Toast.makeText(context, "Project link not available", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "View Project")
        }
        Spacer(modifier = Modifier.height(16.dp))
        Button(
            onClick = {
                if (!repo.contributorsURL.isNullOrEmpty())
                    viewModel.onEvent(RepoInfoEvent.LoadContributors(repo.contributorsURL))
                else {
                    Toast.makeText(context, "No Contributors", Toast.LENGTH_SHORT).show()
                }
            },
            modifier = Modifier.align(Alignment.CenterHorizontally)
        ) {
            Text(text = "View Contributors")
        }

        if (viewModel.state.isLoading) {
            CircularProgressIndicator()
        } else {
            LazyColumn {
                items(viewModel.state.contributors.size) { contributor ->
                    ContributorItem(viewModel.state.contributors[contributor])
                }
            }
        }
    }
}

@Composable
fun ContributorItem(contributor: Contributor) {
    Row(modifier = Modifier
        .padding(16.dp)
        .fillMaxWidth()) {
        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(contributor.avatar_url)
                .crossfade(true)
                .placeholder(R.drawable.ic_launcher_foreground)
                .build(),
            placeholder = painterResource(R.drawable.ic_launcher_foreground),
            contentDescription = "",
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .size(64.dp)
                .clip(CircleShape)
                .border(2.dp, Color.Gray, CircleShape)
        )
        Spacer(modifier = Modifier.width(8.dp))
        Column {
            Text(text = contributor.login, style = MaterialTheme.typography.titleSmall)
            Text(text = "Contributions: ${contributor.contributions}", style = MaterialTheme.typography.bodyMedium)
        }
    }
}

@Preview
@Composable
private fun RepoInfoScreenPreview() {
    RepoInfoScreen(
        repo = RepoListing(
            id = null,
            repoName = "Carl French",
            ownerName = "Bobby Summers",
            projectLink = null,
            ownerImageLink = null,
            description = null,
            contributorsURL = null
        )
    ) {

    }
}