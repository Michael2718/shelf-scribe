package com.example.shelf_scribe.ui.screens.search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.network.VolumeRequestStatus
import com.example.shelf_scribe.ui.screens.ErrorScreen
import com.example.shelf_scribe.ui.screens.LoadingScreen

@Composable
fun SearchDetailsScreen(
    volumeRequestStatus: VolumeRequestStatus,
    context: Context,
    retryAction: () -> Unit,
    modifier: Modifier = Modifier
) {
    when (volumeRequestStatus) {
        is VolumeRequestStatus.Loading -> LoadingScreen(Modifier.fillMaxSize())
        is VolumeRequestStatus.Success -> {
            SearchDetailsScreenContent(
                volume = volumeRequestStatus.volume,
                context = context,
                modifier = modifier
            )
        }
        is VolumeRequestStatus.Error -> ErrorScreen(retryAction, Modifier.fillMaxSize())
    }
}

@Composable
fun SearchDetailsScreenContent(
    volume: ExtendedVolume,
    context: Context,
    modifier: Modifier = Modifier
) {
    val title = volume.volumeInfo.title
    val imageLinks = volume.volumeInfo.imageLinks
    val imageLink = imageLinks?.small ?: (imageLinks?.thumbnail)

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .verticalScroll(scrollState)
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
        ) {
            if (imageLink == null) {
                Image(
                    painter =  painterResource(R.drawable.ic_broken_image),
                    contentDescription = stringResource(R.string.book_thumbnail_is_missing),
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxSize(),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillWidth
                )
            } else {
                AsyncImage(
                    model = ImageRequest.Builder(context = context)
                        .data(imageLink.replace("http", "https"))
                        .crossfade(true)
                        .build(),
                    contentDescription = title,
                    modifier = Modifier
                        .wrapContentSize()
                        .fillMaxSize(),
                    placeholder = painterResource(R.drawable.loading_img),
                    error = painterResource(R.drawable.ic_broken_image),
                    alignment = Alignment.Center,
                    contentScale = ContentScale.FillWidth
                )
            }
        }
        Text(text = "${volume.volumeInfo.title} - ${volume.volumeInfo.authors}")
        volume.volumeInfo.description?.let { Text(text = it) }
    }
}
