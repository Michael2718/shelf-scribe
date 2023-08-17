package com.example.shelf_scribe.ui.screens.search

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.Volume
import com.example.shelf_scribe.network.SearchRequestStatus

@Composable
fun SearchScreen(
    searchRequestStatus: SearchRequestStatus,
    context: Context,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (searchRequestStatus) {
        is SearchRequestStatus.Loading -> Text(text = "Loading")
        is SearchRequestStatus.Success -> {
            ThumbnailsGridScreen(
                volumes = searchRequestStatus.volumes, // getThumbnailsFromVolumes(searchRequestStatus.volumes),
                context = context,
                onVolumeClick = onVolumeClick,
                modifier = modifier
            )
        }
        is SearchRequestStatus.Error -> Text(text = "Error")
    }
}

@Composable
fun ThumbnailsGridScreen(
    volumes: List<Volume>,
    context: Context,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(128.dp),
        modifier = modifier,
        contentPadding = PaddingValues(dimensionResource(R.dimen.padding_small))
    ) {
        items(items = volumes) { volume ->
            ThumbnailCard(
                volume = volume,
                context = context,
                onVolumeClick = onVolumeClick,
                modifier = Modifier
                    .padding(dimensionResource(R.dimen.padding_small))
                    .fillMaxWidth()
                    .aspectRatio(2 / 3f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbnailCard(
    volume: Volume,
    context: Context,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val imageLink = volume.volumeInfo.imageLinks?.thumbnail
    Card(
        onClick = { onVolumeClick(volume.id) },
        modifier = modifier,
        shape = RectangleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        if (imageLink == null) {
            Image(
                painter =  painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.book_thumbnail_is_missing),
                modifier = Modifier.fillMaxSize(),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(imageLink.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.book),
                modifier = Modifier.fillMaxSize(),
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image),
                alignment = Alignment.Center,
                contentScale = ContentScale.Crop
            )
        }
    }
}

//private fun getThumbnailsFromVolumes(volumes: List<Volume>): List<String> {
//    return volumes.map { it.volumeInfo.imageLinks?.thumbnail ?: "" }
//}