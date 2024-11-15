package com.mposadar.kake.cats.view

import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import com.mposadar.kake.cats.CatViewItem
import com.mposadar.kake.cats.domain.Cat

@Composable
fun CatsView(
    modifier: Modifier = Modifier,
    catViewItem: CatViewItem,
) {
    when {
        catViewItem.showGrid -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(minSize = 200.dp),
            ) {
                items(catViewItem.cats.size) { index ->
                    CatView(catViewItem.cats[index].imageUrl)
                }
            }
        }
        else -> {
            LazyColumn(modifier = modifier) {
                items(catViewItem.cats) { cat ->
                    CatView(cat.imageUrl)
                }

                item {
                    CircularProgressIndicator()
                }
            }
        }
    }
}

@Composable
fun CatView(imageUrl: String) {
    // display a placeholder Icon in preview mode
    // or an error image
    if (imageUrl.isNotEmpty()) {
        AsyncImage(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .aspectRatio(1f),
            model = imageUrl,
            contentDescription = "",
            contentScale = ContentScale.Crop,
        )
    } else {
        Icon(
            modifier =
            Modifier
                .fillMaxWidth()
                .fillMaxHeight()
                .padding(8.dp)
                .clip(RoundedCornerShape(8.dp))
                .aspectRatio(1f),
            imageVector = Icons.Default.Build,
            contentDescription = null,
        )
    }
}

@Preview
@Composable
fun CatsViewPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        CatsView(
            catViewItem =
                CatViewItem(
                    showGrid = false,
                    cats = listOf(Cat(""), Cat("")),
                ),
        )
    }
}

@Preview(name = "5-inch Device Landscape", widthDp = 640, heightDp = 360)
@Composable
fun CatsViewGridPreview() {
    Surface(modifier = Modifier.fillMaxSize()) {
        CatsView(
            catViewItem =
                CatViewItem(
                    showGrid = true,
                    cats = listOf(Cat(""), Cat("")),
                ),
        )
    }
}
