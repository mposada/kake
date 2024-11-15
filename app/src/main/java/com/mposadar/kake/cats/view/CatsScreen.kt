package com.mposadar.kake.cats.view

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import com.mposadar.kake.cats.CatsState
import com.mposadar.kake.cats.CatsViewModel

@Composable
fun CatsScreen(
    modifier: Modifier = Modifier,
    viewModel: CatsViewModel
) {
    val collectedState by viewModel.getState().observeAsState()

    when (val state = collectedState) {
        is CatsState.ViewLoaded -> {
            CatsView(modifier, catViewItem = state.viewItem)
        }
        else -> {}
    }
}