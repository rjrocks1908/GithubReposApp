package com.haxon.githubreposapp.presentation.repo_listings

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.haxon.githubreposapp.domain.model.RepoListing

@Composable
fun RepoItem(
    repo: RepoListing,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = repo.repoName,
                fontWeight = FontWeight.SemiBold,
                fontSize = 16.sp,
                color = MaterialTheme.colorScheme.onBackground,
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
            Spacer(modifier = Modifier.height(8.dp))
            Text(
                text = "(${repo.ownerName})",
                fontStyle = FontStyle.Italic,
                color = MaterialTheme.colorScheme.onBackground
            )
        }
    }
}

@Preview(showBackground = true)
@Composable
fun RepoItemPreview() {
    RepoItem(
        repo = RepoListing(
            repoName = "Elise Daugherty",
            ownerName = "Lorie Banks",
            id = 1,
            projectLink = "erroribus",
            ownerImageLink = "cubilia",
            description = "feugait",
            contributorsURL = "https://search.yahoo.com/search?p=fabulas",
        )
    )
}