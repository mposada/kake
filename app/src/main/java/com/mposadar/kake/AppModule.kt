package com.mposadar.kake

import com.mposadar.kake.cats.data.CatApiClient
import com.mposadar.kake.cats.data.CatApiService
import com.mposadar.kake.cats.data.CatRepositoryImpl
import com.mposadar.kake.cats.domain.CatRepository
import com.mposadar.kake.cats.domain.FetchCatsUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(ViewModelComponent::class)
class AppModule {

    @Provides
    fun provideRetrofit(): Retrofit = Retrofit.Builder()
        .baseUrl("https://cataas.com/api/")
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    @Provides
    fun provideCatApiService(retrofit: Retrofit): CatApiService = retrofit.create(CatApiService::class.java)

    @Provides
    @ViewModelScoped
    fun provideCatRepository(catApiClient: CatApiClient): CatRepository = CatRepositoryImpl(catApiClient)

    @Provides
    @ViewModelScoped
    fun provideFetchCatsUseCase(catRepository: CatRepository): FetchCatsUseCase = FetchCatsUseCase(catRepository)
}
