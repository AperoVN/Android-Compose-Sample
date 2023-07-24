package com.apero.sample.data.model

import com.apero.sample.data.provider.imageurl.TmdbImagePathProvider
import kotlin.random.Random

/**
 * Created by KO Huyn on 20/07/2023.
 */
data class Movie(
    val id: Long,
    val adult: Boolean? = null,
    val backdropPath: TmdbImagePathProvider? = null,
    val genreIds: List<Int> = emptyList(),
    val originalLanguage: String? = null,
    val originalTitle: String? = null,
    val overview: String? = null,
    val popularity: Double? = null,
    val posterPath: TmdbImagePathProvider? = null,
    val releaseDate: String? = null,
    val title: String? = null,
    val video: Boolean? = null,
    val voteAverage: Double? = null,
    val voteCount: Int? = null,
) {
    fun getProgress(): Float = voteAverage?.toFloat()?.takeIf { !it.isNaN() } ?: 0f

    companion object {
        fun mock(): Movie {
            return Movie(
                id = Random.nextLong(),
                title = "Transformer: Quái thú nổi dậy",
                releaseDate = "06-06-2023",
                voteCount = 10,
                voteAverage = 73.0
            )
        }
    }
}