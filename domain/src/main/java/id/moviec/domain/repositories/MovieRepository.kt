package id.moviec.domain.repositories

import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.entities.ReviewModel
import id.moviec.domain.entities.VideoModel
import io.reactivex.Flowable

interface MovieRepository {
    fun getPopularMovies(apiKey: String): Flowable<List<MovieModel>?>
    fun getDiscoverMovie(apiKey: String): Flowable<List<MovieModel>?>
    fun getTopRatedMovies(apiKey: String): Flowable<List<MovieModel>?>
    fun getNowPlayingMovies(apiKey: String): Flowable<List<MovieModel>?>
    fun getFavoriteMovies(): Flowable<List<MovieModel>?>
    fun getFavoriteMovie(movieId: Int): Flowable<MovieModel?>
    fun insertFavoriteMovie(movieModel: MovieModel): Long
    fun deleteFavoriteMovie(movieModel: MovieModel): Int
    fun getReviews(
        apiKey: String,
        movieId: Int
    ): Flowable<List<ReviewModel>>

    fun getVideo(
        apiKey: String,
        movieId: Int
    ): Flowable<List<VideoModel>>

    fun getMovieDetails(
        apiKey: String,
        movieId: Int
    ): Flowable<DetailMovieModel>

    fun getSimilarMovie(apiKey: String, movieId: Int): Flowable<List<MovieModel>?>
}