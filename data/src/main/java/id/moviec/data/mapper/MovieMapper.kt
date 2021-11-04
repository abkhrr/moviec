package id.moviec.data.mapper

import id.moviec.domain.dto.MovieDto
import id.moviec.domain.entities.MovieModel

class MovieMapper : Mapper<MovieDto?, List<MovieModel>?>() {
    override fun apply(from: MovieDto?): List<MovieModel>? {
        return from?.results?.map { movie ->
            MovieModel(
                movie.voteCount,
                movie.id,
                movie.video,
                movie.voteAverage,
                movie.originalTitle,
                movie.popularity,
                movie.posterPath,
                movie.originalLanguage,
                movie.originalTitle,
                movie.genreIds,
                movie.backdropPath,
                movie.adult,
                movie.overview,
                movie.releaseDate
            )
        }
    }
}