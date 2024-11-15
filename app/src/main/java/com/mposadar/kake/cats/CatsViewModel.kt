package com.mposadar.kake.cats

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.distinctUntilChanged
import androidx.lifecycle.viewModelScope
import com.mposadar.kake.cats.domain.FetchCatsUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CatsViewModel
    @Inject
    constructor(
        private val fetchCatsUseCase: FetchCatsUseCase,
    ) : ViewModel() {
        private val state: MutableLiveData<CatsState> = MutableLiveData()

        init {
            fetchState()
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
                        viewItem = currentState.viewItem.copy(showGrid = !currentState.viewItem.showGrid),
                    )
            }
        }

        fun fetchMoreCats() {
            (state.value as CatsState.ViewLoaded).let {
                if (it.isLoading) return
                state.value = it.copy(isLoading = true)

                viewModelScope.launch {
                    fetchCats(
                        limit = it.currentLimit + 10,
                        skip = it.currentLimit
                    )
                }
            }
        }

        private fun fetchState() {
            viewModelScope.launch { fetchCats() }
        }

        private suspend fun fetchCats(
            limit: Int = 10,
            skip: Int = 0,
        ) {
            val result = fetchCatsUseCase.invoke(limit, skip)
            result.onSuccess { cats ->
                state.value =
                    CatsState.ViewLoaded(
                        currentLimit = limit,
                        currentSkip = skip,
                        isLoading = false,
                        viewItem = CatViewItem(cats = cats),
                    )
            }
            result.onFailure { error ->
                // TODO : handle error
            }
        }
    }
