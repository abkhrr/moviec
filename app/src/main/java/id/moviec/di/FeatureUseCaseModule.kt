package id.moviec.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import id.moviec.domain.PostExecutionThread
import id.moviec.domain.interactors.*
import id.moviec.domain.repositories.MovieRepository

@Module
@InstallIn(ViewModelComponent::class)
object FeatureUseCaseModule {
    @Provides
    @ViewModelScoped
    fun provideGetPopularMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetPopularMovies {
        return GetPopularMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetDiscoverMovie(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetDiscoverMovie {
        return GetDiscoverMovie(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetNowPlayingMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetNowPlayingMovies {
        return GetNowPlayingMovies(movieRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetTopRatedMovies(
        movieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetTopRatedMovies {
        return GetTopRatedMovies(movieRepository, postExecutionThread)
    }

    // Movie detail
    @Provides
    @ViewModelScoped
    fun provideGetDetailMovie(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetDetailMovie {
        return GetDetailMovie(movieDetailRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetFavoriteMovie(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetFavoriteMovie {
        return GetFavoriteMovie(movieDetailRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideInsertFavoriteMovie(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): InsertFavoriteMovie {
        return InsertFavoriteMovie(movieDetailRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideDeleteFavoriteMovie(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): DeleteFavoriteMovie {
        return DeleteFavoriteMovie(movieDetailRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetReviews(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetReviews {
        return GetReviews(movieDetailRepository, postExecutionThread)
    }

    @Provides
    @ViewModelScoped
    fun provideGetVideos(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetVideos {
        return GetVideos(movieDetailRepository, postExecutionThread)
    }

    // Fav
    @Provides
    @ViewModelScoped
    fun provideGetFavoriteMovies(
        favoriteMovieRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetFavoriteMovies {
        return GetFavoriteMovies(favoriteMovieRepository, postExecutionThread)
    }

    // Similar
    @Provides
    @ViewModelScoped
    fun provideGetSimilarMovie(
        movieDetailRepository: MovieRepository,
        postExecutionThread: PostExecutionThread
    ): GetSimilarMovie {
        return GetSimilarMovie(movieDetailRepository, postExecutionThread)
    }
}