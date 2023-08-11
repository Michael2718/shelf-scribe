package com.example.shelf_scribe.ui.screens

import androidx.annotation.DrawableRes
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
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.shelf_scribe.R
import com.example.shelf_scribe.model.Book
import com.example.shelf_scribe.ui.theme.ShelfScribeTheme

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    ThumbnailsGridScreen(
        books = listOf(
            Book(
                id = "1",
                thumbnailResourceId = R.drawable._1
            ),
            Book(
                id = "2",
                thumbnailResourceId = R.drawable._2
            ),
            Book(
                id = "3",
                thumbnailResourceId = R.drawable._3
            ),
            Book(
                id = "4",
                thumbnailResourceId = R.drawable._4
            ),
            Book(
                id = "5",
                thumbnailResourceId = R.drawable._1
            ),
            Book(
                id = "6",
                thumbnailResourceId = R.drawable._2
            ),
            Book(
                id = "7",
                thumbnailResourceId = R.drawable._3
            ),
            Book(
                id = "8",
                thumbnailResourceId = R.drawable._4
            )
        ),
        modifier = modifier.fillMaxWidth()
    )
}

@Composable
fun ThumbnailsGridScreen(
    books: List<Book>,
    modifier: Modifier = Modifier
) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(144.dp),
        modifier = modifier,
        contentPadding = PaddingValues(4.dp)
    ) {
        items(items = books, key = { book -> book.id }) { book ->
            ThumbnailCard(
                thumbnail = book.thumbnailResourceId,
                modifier = Modifier
                    .padding(2.dp)
                    .fillMaxWidth()
                    .aspectRatio(2/3f)
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ThumbnailCard(
    @DrawableRes
    thumbnail: Int,
    modifier: Modifier = Modifier
) {
    Card(
        onClick = { /*TODO*/ },
        modifier = modifier,
        shape = RectangleShape,
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary)
    ) {
        Image(
            painter = painterResource(thumbnail),
            contentDescription = null,
            modifier = Modifier.fillMaxSize(),
            alignment = Alignment.Center,
            contentScale = ContentScale.Crop
        )
    }
}

@Preview(showBackground = true)
@Composable
fun ThumbnailsGridScreenPreview() {
    ShelfScribeTheme {
        ThumbnailsGridScreen(
            books = listOf(
                Book(
                    id = "1",
                    thumbnailResourceId = R.drawable._1
                ),
                Book(
                    id = "2",
                    thumbnailResourceId = R.drawable._2
                ),
                Book(
                    id = "3",
                    thumbnailResourceId = R.drawable._3
                ),
                Book(
                    id = "4",
                    thumbnailResourceId = R.drawable._4
                ),
                Book(
                    id = "5",
                    thumbnailResourceId = R.drawable._1
                ),
                Book(
                    id = "6",
                    thumbnailResourceId = R.drawable._2
                ),
                Book(
                    id = "7",
                    thumbnailResourceId = R.drawable._3
                ),
                Book(
                    id = "8",
                    thumbnailResourceId = R.drawable._4
                )
            ),
            modifier = Modifier.fillMaxSize()
        )
    }
}