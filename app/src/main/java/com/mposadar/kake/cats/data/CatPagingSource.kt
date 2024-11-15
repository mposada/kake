package com.mposadar.kake.cats.data

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.mposadar.kake.cats.domain.Cat
import com.mposadar.kake.cats.domain.FetchCatsUseCase
import kotlinx.coroutines.delay
import javax.inject.Inject

class CatPagingSource
    @Inject
    constructor(
        private val fetchCatsUseCase: FetchCatsUseCase,
    ) : PagingSource<Int, Cat>() {
        override fun getRefreshKey(state: PagingState<Int, Cat>): Int? =
            state.anchorPosition?.let { anchor ->
                state.closestPageToPosition(anchor)?.prevKey?.plus(1)
                    ?: state.closestPageToPosition(anchor)?.nextKey?.minus(1)
            }

        override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Cat> =
            try {
                val currentPage = params.key ?: 0
                val limit = params.loadSize
                val skip = currentPage * limit
                delay(3000)
                val result = fetchCatsUseCase.invoke(limit, skip)
                result.fold(
                    onSuccess = { cats ->
                        LoadResult.Page(
                            data = cats,
                            prevKey = null, // Only paging forward.
                            nextKey = if (cats.isEmpty()) null else currentPage + 1,
                        )
                    },
                    onFailure = { error ->
                        LoadResult.Error(error)
                    },
                )
            } catch (error: Exception) {
                LoadResult.Error(error)
            }
    }
