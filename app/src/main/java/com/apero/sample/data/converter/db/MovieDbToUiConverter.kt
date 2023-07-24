package com.apero.sample.data.converter.db

import com.apero.sample.data.converter.IConverter
import com.apero.sample.data.db.model.MovieEntity
import com.apero.sample.data.model.Movie
import com.apero.sample.data.provider.imageurl.TmdbImagePathProvider

/**
 * Created by KO Huyn on 20/07/2023.
 */
object MovieDbToUiConverter : IConverter<MovieEntity, Movie> {
    override fun convert(source: MovieEntity): Movie {
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