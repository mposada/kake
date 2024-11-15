package com.mposadar.kake.cats.domain

data class Cat(
    val id: String,
) {
    val imageUrl: String
        get() {
            return if (id.isNotEmpty()) "https://cataas.com/cat/$id" else ""
        }
}