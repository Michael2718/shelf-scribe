package com.example.shelf_scribe.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier
) {
    Column {
        Text(text = "Trending")
        Divider(
            modifier = modifier.fillMaxWidth(),
            thickness = 1.dp
        )
        Text(text = "Fantasy")
    }
}
