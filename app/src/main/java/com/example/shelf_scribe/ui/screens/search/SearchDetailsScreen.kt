package com.example.shelf_scribe.ui.screens.search

import android.content.Context
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
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
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            ImageCard(
                imageLink = imageLink,
                context = context,
                modifier = Modifier
//                    .wrapContentWidth()
                    .wrapContentSize()
                    .padding(dimensionResource(R.dimen.padding_small))
            )
//            Image(imageVector = Icons.Default.AccountBox, contentDescription = null)
            Column(
                modifier = Modifier.fillMaxSize()
            ) {
                Text(
                    text = title,
                    fontWeight = FontWeight.Bold,
                    style = MaterialTheme.typography.titleLarge
                )
                Text(
                    text = volume.volumeInfo.authors[0],
                    style = MaterialTheme.typography.bodyLarge
                )
//                volume.volumeInfo.description?.let { Text(text = it) }
            }
        }
    }
}

@Composable
fun ImageCard(
    imageLink: String?,
    context: Context,
    modifier: Modifier = Modifier
) {
    Card(
        modifier = modifier,
        shape = RectangleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (imageLink == null) {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.book_thumbnail_is_missing),
                modifier = Modifier,
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(imageLink.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.book_image),
//                modifier = Modifier
//                    .wrapContentSize()
//                    .fillMaxSize(),
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillWidth
            )
        }
    }
}