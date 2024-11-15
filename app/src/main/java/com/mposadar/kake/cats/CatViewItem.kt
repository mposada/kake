package com.mposadar.kake.cats

import com.mposadar.kake.cats.domain.Cat

data class CatViewItem(
    val cats: List<Cat>,
    val showGrid: Boolean = false,
)