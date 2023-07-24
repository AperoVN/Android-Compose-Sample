package com.apero.sample.data.converter.api

import com.apero.sample.data.converter.IConverter
import com.apero.sample.data.model.Movie
import com.apero.sample.data.network.response.movie.MovieItemResponse
import com.apero.sample.data.provider.imageurl.TmdbImagePathProvider

/**
 * Created by KO Huyn on 20/07/2023.
 */
object MovieApiToUiConverter : IConverter<MovieItemResponse, Movie?> {
    override fun convert(source: MovieItemResponse): Movie? {
        if (source.id == null) return null
        return Movie(
            id = source.id,
            adult = source.adult,
            backdropPath = TmdbImagePathProvider(source.backdropPath),
            genreIds = source.genreIds,
            originalLanguage = source.originalLanguage,
            originalTitle = source.originalTitle,
            overview = source.overview,
            popularity = source.popularity,
            posterPath = TmdbImagePathProvider(source.posterPath),
            releaseDate = source.releaseDate,
            title = source.title,
            video = source.video,
            voteAverage = source.voteAverage,
            voteCount = source.voteCount
        )
    }
}