package id.moviec.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import id.moviec.MoviecApplication
import id.moviec.base.config.WebApiProvider
import id.moviec.data.local.db.MovieDao
import id.moviec.data.mapper.DetailMovieMapper
import id.moviec.data.mapper.MovieMapper
import id.moviec.data.mapper.ReviewMapper
import id.moviec.data.mapper.VideoMapper
import id.moviec.data.remote.MovieApi
import id.moviec.data.repositories.MovieRepositoryImpl
import id.moviec.domain.repositories.MovieRepository
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    @Singleton
    @Provides
    fun provideWebApiProvider(): WebApiProvider = WebApiProvider

    @Singleton
    @Provides
    fun provideRetrofit(
        webApiProvider: WebApiProvider,
        myApplication: MoviecApplication,
    ): Retrofit = webApiProvider.getRetrofit(myApplication.getBaseUrl(), myApplication.applicationContext)

    // App Api Module Instance

    @Singleton
    @Provides
    fun provideMovieRepository(movieApi: MovieApi, movieDao: MovieDao): MovieRepository =
        MovieRepositoryImpl(
            movieApi, movieDao, MovieMapper(), ReviewMapper(),
            VideoMapper(), DetailMovieMapper()
        )

    @Singleton
    @Provides
    fun provideMovieApi(retrofit: Retrofit): MovieApi = retrofit.create(
        MovieApi::class.java
    )

}