package com.mposadar.kake.cats

sealed class CatsState {
    data class ViewLoaded(
        val viewItem: CatViewItem,
        val isLoading: Boolean = false,
        val currentLimit: Int = 10,
        val currentSkip: Int = 0,
    ) : CatsState()
}
