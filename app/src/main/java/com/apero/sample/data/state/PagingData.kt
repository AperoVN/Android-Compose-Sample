package com.apero.sample.data.state

data class PagingData<T>(
    val list: List<T> = emptyList(),
    val page: Int? = null,
    val totalPage: Int? = null,
    val pagingState: PagingState = PagingState.IDLE
) {
    val canLoadPage get() = if (page != null && totalPage != null) page < totalPage else false

    companion object {
        fun <T> createLoading(page: Int?): PagingData<T> {
            return PagingData(pagingState = PagingState.createLoading(page))
        }

        fun <T> createError(page: Int?, failureState: FailureState): PagingData<T> {
            return PagingData(pagingState = PagingState.createError(page, failureState))
        }
    }
}

sealed class PagingState {
    object IDLE : PagingState()
    sealed class LoadState : PagingState() {
        object LoadMore : LoadState()
        object Loading : LoadState()
    }

    sealed class Failure(failureState: FailureState) : PagingState() {
        data class Error(val failureState: FailureState) : Failure(failureState)
        data class PaginationExhaust(val failureState: FailureState) : Failure(failureState)
    }

    fun loadPageReady(): Boolean = this is IDLE

    companion object {
        fun createError(page: Int?, failureState: FailureState): PagingState {
            return when (page) {
                null, 1 -> Failure.Error(failureState)
                else -> Failure.PaginationExhaust(failureState)
            }
        }

        fun createLoading(page: Int?): LoadState {
            return when (page) {
                null, 1 -> LoadState.Loading
                else -> LoadState.LoadMore
            }
        }
    }
}
