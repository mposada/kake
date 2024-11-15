package com.mposadar.kake.cats

sealed class CatsState {
    data class ViewLoaded(val viewItem: CatViewItem) : CatsState()
}
