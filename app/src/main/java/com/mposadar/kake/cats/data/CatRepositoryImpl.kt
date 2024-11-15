package com.mposadar.kake.cats.data

import com.mposadar.kake.cats.domain.Cat
import com.mposadar.kake.cats.domain.CatRepository
import javax.inject.Inject

class CatRepositoryImpl
    @Inject
    constructor(
        private val apiClient: CatApiClient,
    ) : CatRepository {
        override suspend fun fetchCats(
            limit: Int,
            skip: Int,
        ): Result<List<Cat>> =
            apiClient.fetchCats(limit, skip).map { dataModels ->
                dataModels.map { dataModel ->
                    Cat(id = dataModel.id)
                }
            }
    }