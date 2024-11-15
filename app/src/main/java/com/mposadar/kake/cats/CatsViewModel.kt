package com.mposadar.kake.cats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.mposadar.kake.cats.data.CatPagingSource
import com.mposadar.kake.cats.domain.Cat
import com.mposadar.kake.cats.domain.FetchCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class CatsViewModel
    @Inject
    constructor(
        private val fetchCatsUseCase: FetchCatsUseCase,
    ) : ViewModel() {
        val pagedCats: Flow<PagingData<Cat>> =
            Pager(
                config = PagingConfig(pageSize = 10, initialLoadSize = 10, enablePlaceholders = false),
                pagingSourceFactory = { CatPagingSource(fetchCatsUseCase) },
            ).flow.cachedIn(viewModelScope)

        private val state: MutableLiveData<CatsState> = MutableLiveData()

        init {
            state.value = CatsState.ViewLoaded(viewItem = CatViewItem(showGrid = false))
        }

        fun getState(): LiveData<CatsState> =
            state
                .distinctUntilChanged()
                .also {
                    Log.d("CatsViewModel", "${this.javaClass.simpleName}: Get state: ${state.value}")
                }

        fun handleEvent() {
            val currentState = state.value
            if (currentState is CatsState.ViewLoaded) {
                state.value =
                    currentState.copy(
                        viewItem = currentState.viewItem.copy(
                            showGrid = !currentState.viewItem.showGrid
                        ),
                    )
            }
        }
    }
