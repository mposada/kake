package com.mposadar.kake.cats.view

import android.content.res.Configuration
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Build
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.items
import coil3.compose.AsyncImage
import com.mposadar.kake.cats.CatViewItem
import com.mposadar.kake.cats.domain.Cat

@Composable
fun CatsView(
    modifier: Modifier = Modifier,
    catViewItem: CatViewItem,
    cats: LazyPagingItems<Cat>,
) {
    when {
        isLandscape() || catViewItem.showGrid -> {
            LazyVerticalGrid(
                modifier = modifier,
                columns = GridCells.Adaptive(minSize = 200.dp),
            ) {
                items(cats.itemSnapshotList) { cat ->
                    cat?.let { CatView(imageUrl = it.imageUrl) }
                }
            }
        }
        else -> {
            LazyColumn(modifier = modifier) {
                items(cats) { cat ->
                    cat?.let { CatView(imageUrl = it.imageUrl) }
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

@Composable
fun isLandscape(): Boolean {
    val configuration = LocalConfiguration.current
    return configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
}
