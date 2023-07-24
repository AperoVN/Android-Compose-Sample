package com.apero.sample.data.converter.ui

import com.apero.sample.data.converter.IConverter
import com.apero.sample.data.db.model.MovieEntity
import com.apero.sample.data.model.Movie

/**
 * Created by KO Huyn on 20/07/2023.
 */
object MovieUiToDbConverter : IConverter<Movie, MovieEntity> {
    override fun convert(source: Movie): MovieEntity {
        return MovieEntity(
            id = source.id,
            adult = source.adult,
            backdropPath = source.backdropPath?.imagePath,
            genreIds = source.genreIds,
            originalLanguage = source.originalLanguage,
            originalTitle = source.originalTitle,
            overview = source.overview,
            popularity = source.popularity,
            posterPath = source.posterPath?.imagePath,
            releaseDate = source.releaseDate,
            title = source.title,
            video = source.video,
            voteAverage = source.voteAverage,
            voteCount = source.voteCount
        )
    }
}