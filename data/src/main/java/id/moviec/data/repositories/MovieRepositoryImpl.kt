package id.moviec.data.repositories

import id.moviec.data.local.db.MovieDao
import id.moviec.data.mapper.DetailMovieMapper
import id.moviec.data.mapper.MovieMapper
import id.moviec.data.mapper.ReviewMapper
import id.moviec.data.mapper.VideoMapper
import id.moviec.data.remote.MovieApi
import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.entities.ReviewModel
import id.moviec.domain.entities.VideoModel
import id.moviec.domain.repositories.MovieRepository
import io.reactivex.Flowable
import javax.inject.Inject

class MovieRepositoryImpl @Inject constructor(
    private val api: MovieApi,
    private val movieDao: MovieDao,
    private val movieMapper: MovieMapper,
    private val reviewMapper: ReviewMapper,
    private val videoMapper: VideoMapper,
    private val detailMovieMapper: DetailMovieMapper
) : MovieRepository {

    override fun getPopularMovies(apiKey: String): Flowable<List<MovieModel>?> =
        api.getPopularMovies(apiKey).map(movieMapper)

    override fun getDiscoverMovie(apiKey: String): Flowable<List<MovieModel>?> =
        api.getDiscoverMovie(apiKey).map(movieMapper)

    override fun getTopRatedMovies(apiKey: String): Flowable<List<MovieModel>?> =
        api.getTopRatedMovies(apiKey).map(movieMapper)

    override fun getNowPlayingMovies(apiKey: String): Flowable<List<MovieModel>?> =
        api.getNowPlayingMovies(apiKey).map(movieMapper)

    override fun getReviews(apiKey: String, movieId: Int): Flowable<List<ReviewModel>> =
        api.getReviews(movieId, apiKey).map(reviewMapper)

    override fun getVideo(apiKey: String, movieId: Int): Flowable<List<VideoModel>> =
        api.getVideo(movieId, apiKey).map(videoMapper)

    override fun getMovieDetails(apiKey: String, movieId: Int): Flowable<DetailMovieModel> =
        api.getMovieDetails(movieId, apiKey).map(detailMovieMapper)

    override fun getSimilarMovie(apiKey: String, movieId: Int): Flowable<List<MovieModel>?> =
        api.getSimilarMovie(movieId, apiKey).map(movieMapper)

    override fun getFavoriteMovies(): Flowable<List<MovieModel>?> =
        movieDao.selectFavoriteMovie()

    override fun getFavoriteMovie(movieId: Int): Flowable<MovieModel?> =
        movieDao.select(movieId)

    override fun insertFavoriteMovie(movieModel: MovieModel) =
        movieDao.insert(movieModel)

    override fun deleteFavoriteMovie(movieModel: MovieModel): Int =
        movieDao.delete(movieModel)
}