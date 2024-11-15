package com.mposadar.kake.cats.view

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.compose.collectAsLazyPagingItems
import com.mposadar.kake.cats.CatsState
import com.mposadar.kake.cats.CatsViewModel

@Composable
fun CatsScreen(
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel,
) {
    val cats = viewModel.pagedCats.collectAsLazyPagingItems()
    val collectedState by viewModel.getState().observeAsState()

    println("mposadar: STATE append --> ${cats.loadState.append is LoadState.Loading}")
    println("mposadar: STATE prepend --> ${cats.loadState.prepend is LoadState.Loading}")
    println("mposadar: STATE refresh --> ${cats.loadState.refresh is LoadState.Loading}")

    when (val state = collectedState) {
        is CatsState.ViewLoaded -> {
            Box(modifier = Modifier.fillMaxSize()) {
                CatsView(modifier, catViewItem = state.viewItem, cats = cats)
                if (cats.loadState.append is LoadState.Loading) {
                    CircularProgressIndicator(
                        modifier = Modifier
                            .padding(32.dp)
                            .align(Alignment.BottomCenter),
                    )
                }
            }
        }
        else -> {}
    }
}
