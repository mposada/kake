package com.mposadar.kake.cats.domain

interface CatRepository {
    suspend fun fetchCats(
        limit: Int,
        skip: Int,
    ): Result<List<Cat>>
}
