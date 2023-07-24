package com.apero.sample.data.network.response.base

import com.google.gson.annotations.SerializedName

data class ListResponse<T : Any>(
    @SerializedName("page")
    val page: Int? = null,
    @SerializedName("results")
    val results: T? = null,
    @SerializedName("total_pages")
    val totalPages: Int? = null,
    @SerializedName("total_results")
    val totalResults: Int? = null
) {
    val nextPage: Int? get() = if (page == null || totalPages == null || page >= totalPages) null else page.inc()

    val prevPage: Int? get() = if (page == null || page == 1) null else page.dec()

    val isNextPage: Boolean get() = page != null && totalPages != null && page < totalPages
}