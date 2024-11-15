package com.mposadar.kake.cats.data

import javax.inject.Inject

class CatApiClient
    @Inject
    constructor(
        private val catApiService: CatApiService,
    ) {
        suspend fun fetchCats(
            limit: Int,
            skip: Int,
        ): Result<List<CatDataModel>> =
            try {
                val cats = catApiService.fetchCats(limit, skip)
                Result.success(cats)
            } catch (e: Exception) {
                Result.failure(e)
            }
    }