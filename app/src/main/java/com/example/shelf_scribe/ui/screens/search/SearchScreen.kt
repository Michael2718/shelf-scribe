package com.example.shelf_scribe.ui.screens.search

import android.content.Context
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.Volume
import com.example.shelf_scribe.network.SearchRequestStatus
import com.example.shelf_scribe.ui.components.InfiniteAnimation
import com.example.shelf_scribe.ui.components.ThumbnailCard
import com.example.shelf_scribe.ui.screens.ErrorScreen
import com.example.shelf_scribe.ui.screens.LoadingScreen
import com.example.shelf_scribe.ui.theme.ShelfScribeTheme

@Composable
fun SearchScreen(
    searchRequestStatus: SearchRequestStatus,
    context: Context,
    retryAction: () -> Unit,
    onVolumeClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    when (searchRequestStatus) {
        is SearchRequestStatus.Start -> StartScreen(
            modifier = modifier.fillMaxSize()//.fillMaxSize()
        )
        is SearchRequestStatus.Loading -> LoadingScreen(modifier.fillMaxSize())
        is SearchRequestStatus.Success -> {
            ThumbnailsGridScreen(
                volumes = searchRequestStatus.volumes, // getThumbnailsFromVolumes(searchRequestStatus.volumes),
                context = context,
                onVolumeClick = onVolumeClick,
                modifier = modifier
            )
        }

        is SearchRequestStatus.Error -> ErrorScreen(retryAction, modifier.fillMaxSize())
    }
}

@Composable
private fun StartScreen(
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        InfiniteAnimation(
            animationResId = R.raw.animation_bookshelf_color,
            modifier = Modifier
                .height(280.dp)
        )

        Text(
            text = "Search for free Google Books",
            style = MaterialTheme.typography.titleLarge
        )
    }
}

@Composable
private fun ThumbnailsGridScreen(
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

//private fun getThumbnailsFromVolumes(volumes: List<Volume>): List<String> {
//    return volumes.map { it.volumeInfo.imageLinks?.thumbnail ?: "" }
//}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun StartScreenDarkThemePreview() {
    ShelfScribeTheme(
        darkTheme = true
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            StartScreen()
        }
    }
}
