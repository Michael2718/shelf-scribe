package com.example.shelf_scribe.ui.screens

import android.content.Context
import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.unit.dp
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.Volume
import com.example.shelf_scribe.network.SubjectsRequestStatus
import com.example.shelf_scribe.ui.components.ThumbnailCard

@Composable
fun HomeScreen(
    subjectsRequestStatus: SubjectsRequestStatus,
    context: Context,
    retryAction: () -> Unit,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (subjectsRequestStatus) {
        is SubjectsRequestStatus.Loading -> LoadingScreen(modifier.fillMaxSize())
        is SubjectsRequestStatus.Success -> {
            Subjects(
                subjects = subjectsRequestStatus.subjects,
                context = context,
                onVolumeClick = onVolumeClick,
                modifier = modifier.fillMaxSize()
            )
        }

        is SubjectsRequestStatus.Error -> ErrorScreen(retryAction, modifier.fillMaxSize())
    }
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun Subjects(
    subjects: Map<String, List<Volume>>,
    context: Context,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keys = remember {
        subjects.keys.toList()
    }

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small)),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(keys) { subject ->
            SubjectsListItem(
                title = subject,
                volumes = subjects[subject] ?: listOf(),
                context = context,
                onVolumeClick = onVolumeClick,
                modifier = Modifier
                    .animateItemPlacement()
                    .padding(dimensionResource(R.dimen.padding_small))
            )
        }
    }
}

@Composable
fun SubjectsListItem(
    title: String,
    volumes: List<Volume>,
    context: Context,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        ListItem(
            headlineContent = {
                Text(title, style = MaterialTheme.typography.titleLarge)
            },
        )
        LazyRow(
            modifier = Modifier.height(180.dp),
            horizontalArrangement = Arrangement.spacedBy(dimensionResource(R.dimen.padding_medium))
//                    contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
        ) {
            items(volumes) { volume ->
                ThumbnailCard(
                    volume = volume,
                    context = context,
                    onVolumeClick = onVolumeClick,
                    modifier = Modifier
                        .fillMaxWidth()
                        .aspectRatio(2 / 3f)
                        .animateContentSize()
                )
            }
        }
    }
}