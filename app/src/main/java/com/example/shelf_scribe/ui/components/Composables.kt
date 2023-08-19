package com.example.shelf_scribe.ui.components

import android.content.Context
import androidx.annotation.RawRes
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.graphics.BlendModeColorFilterCompat
import androidx.core.graphics.BlendModeCompat
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.airbnb.lottie.LottieProperty
import com.airbnb.lottie.compose.LottieAnimation
import com.airbnb.lottie.compose.LottieCompositionSpec
import com.airbnb.lottie.compose.LottieConstants
import com.airbnb.lottie.compose.rememberLottieComposition
import com.airbnb.lottie.compose.rememberLottieDynamicProperties
import com.airbnb.lottie.compose.rememberLottieDynamicProperty
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.Volume

@Composable
fun InfiniteAnimation(
    @RawRes animationResId: Int,
    modifier: Modifier = Modifier,
    color: Color = Color.Unspecified
) {
    val composition by rememberLottieComposition(LottieCompositionSpec.RawRes(animationResId))
    val dynamicProperties = rememberLottieDynamicProperties(
        rememberLottieDynamicProperty(
            property = LottieProperty.COLOR_FILTER,
            value = BlendModeColorFilterCompat.createBlendModeColorFilterCompat(
                color.hashCode(),
                BlendModeCompat.SRC_ATOP
            ),
            keyPath = arrayOf(
                "**"
            )
        )
    )

    LottieAnimation(
        composition = composition,
        modifier = modifier,
        iterations = LottieConstants.IterateForever,
        dynamicProperties = dynamicProperties
    )
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
                painter = painterResource(R.drawable.ic_broken_image),
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
