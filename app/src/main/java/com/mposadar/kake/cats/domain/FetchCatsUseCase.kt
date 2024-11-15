package com.mposadar.kake.cats.domain

class FetchCatsUseCase(
    private val catRepository: CatRepository
) {
    suspend operator fun invoke(
        limit: Int,
        skip: Int,
    ): Result<List<Cat>> = catRepository.fetchCats(limit, skip)
}