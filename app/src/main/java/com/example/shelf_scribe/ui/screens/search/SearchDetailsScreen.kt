package com.example.shelf_scribe.ui.screens.search

import android.content.Context
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.ListItem
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.api.ExtendedVolume
import com.example.shelf_scribe.model.api.ExtendedVolumeInfo
import com.example.shelf_scribe.network.VolumeRequestStatus
import com.example.shelf_scribe.ui.screens.ErrorScreen
import com.example.shelf_scribe.ui.screens.LoadingScreen
import com.example.shelf_scribe.ui.theme.ShelfScribeTheme
import de.charlex.compose.HtmlText

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
    val author = volume.volumeInfo.authors[0] // Assuming, that book has one main author
    val publisher = volume.volumeInfo.publisher
    val publishedDate = volume.volumeInfo.publishedDate
    val description = volume.volumeInfo.description
    val pageCount = volume.volumeInfo.pageCount
    val imageLinks = volume.volumeInfo.imageLinks
    val imageLink = imageLinks?.medium ?: (imageLinks?.small ?: imageLinks?.thumbnail)

    val scrollState = rememberScrollState()
    Column(
        modifier = modifier
            .padding(dimensionResource(R.dimen.padding_small))
            .verticalScroll(scrollState)
    ) {
        ListItem(
            headlineContent = {
                ImageCard(
                    imageLink = imageLink,
                    context = context,
                    modifier = Modifier
                        .height(320.dp)
                )
            }
        )
        ListItem(
            headlineContent = {
                Text(
                    text = title,
                    fontWeight = FontWeight.Light,
                    style = MaterialTheme.typography.headlineLarge
                )
            }
        )
        ListItem(
            headlineContent = { Text(author, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text("Author") }
        )
        ListItem(
            headlineContent = { Text(publisher, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text("Publisher") }
        )
        ListItem(
            headlineContent = { Text(publishedDate, fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text("Published date") }
        )
        ListItem(
            headlineContent = { Text(pageCount.toString(), fontWeight = FontWeight.SemiBold) },
            supportingContent = { Text("Pages") }
        )
        Divider(modifier = Modifier.fillMaxWidth(), thickness = 1.dp)
        ListItem(
            headlineContent = {
                Text(
                    text = "About this book",
                    style = MaterialTheme.typography.titleLarge
                )
            }
        )
        ListItem(
            headlineContent = {
                HtmlText(
                    text = description,
                    color = MaterialTheme.colorScheme.onSurface,
                    style = MaterialTheme.typography.bodyLarge
                )
            }
        )
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
//        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        if (imageLink == null) {
            Image(
                painter = painterResource(R.drawable.ic_broken_image),
                contentDescription = stringResource(R.string.book_thumbnail_is_missing),
                modifier = Modifier
                    .fillMaxHeight(),
                alignment = Alignment.Center,
                contentScale = ContentScale.FillHeight
            )
        } else {
            AsyncImage(
                model = ImageRequest.Builder(context = context)
                    .data(imageLink.replace("http", "https"))
                    .crossfade(true)
                    .build(),
                contentDescription = stringResource(R.string.book_image),
                modifier = Modifier
                    .fillMaxHeight(),
                placeholder = painterResource(R.drawable.loading_img),
                error = painterResource(R.drawable.ic_broken_image),
                alignment = Alignment.Center,
                contentScale = ContentScale.Fit
            )
        }
    }
}

@Preview(showSystemUi = true, showBackground = true)
@Composable
fun SearchDetailsScreenContentPreview() {
    val volume = ExtendedVolume(
        id = "3Cy7DwAAQBAJ",
        volumeInfo = ExtendedVolumeInfo(
            title = "Pride and Prejudice",
            authors = listOf("Jane Austen"),
            publisher = "Oxford University Press",
            publishedDate = "2019-11-05",
            description = "He began to feel the danger of paying Elizabeth too much attention.\"<br> <br> Pride and Prejudice , one of the most famous love stories of all time, has also proven itself as a treasured mainstay of the English literary canon. With the arrival of eligible young men in their neighbourhood, the lives of Mr. and Mrs. Bennet and their five daughters are turned inside out and upside down. Pride encounters prejudice, upward-mobility confronts social disdain, and quick-wittedness challenges sagacity. Misconceptions and hasty judgements bring heartache and scandal, but eventually lead to true understanding, self-knowledge, and love.<br> <br> It's almost impossible to open Pride and Prejudice without feeling the pressure of so many readers having known and loved this novel already. Will you fail the test - or will you love it too? As a story that celebrates more unflinchingly than any of Austen's other novels the happy meeting-of-true-minds, and one that has attracted the most fans over the centuries, Pride and Prejudice sets up an echo chamber of good feelings in which romantic love and the love of reading amplify each other.<br>",
            pageCount = 3,
//            imageLinks = ExtendedImageLinks(
//                thumbnail = "http://books.google.com/books/publisher/content?id=3Cy7DwAAQBAJ&printsec=frontcover&img=1&zoom=5&edge=curl&imgtk=AFLRE72CCTUy7L9lE_1MYVFoQy3pFs_LvjJcZAan4mc0EIu-osHwEYXT5AGyGzb3I7K2t1_Jro7fbcR2acE0WHiLmd0iqcmqOe4KyNFMhbTmp6BXQfLbcLaqYZKIs9Y75mHtl4fENN_D&source=gbs_api"
//            )
        )
    )
    ShelfScribeTheme {
        SearchDetailsScreenContent(
            volume = volume,
            context = LocalContext.current,
            modifier = Modifier.padding(4.dp)
        )
    }
}