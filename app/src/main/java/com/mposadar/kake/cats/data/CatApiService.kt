package com.mposadar.kake.cats.data

import retrofit2.http.GET
import retrofit2.http.Query

interface CatApiService {
    @GET("cats")
    suspend fun fetchCats(
        @Query("limit") limit: Int,
        @Query("skip") skip: Int,
    ): List<CatDataModel>
}