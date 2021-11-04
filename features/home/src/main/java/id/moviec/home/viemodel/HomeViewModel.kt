package id.moviec.home.viemodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.moviec.base.viewmodel.BaseViewModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.interactors.GetDiscoverMovie
import id.moviec.domain.interactors.GetNowPlayingMovies
import id.moviec.domain.interactors.GetPopularMovies
import id.moviec.domain.interactors.GetTopRatedMovies
import id.moviec.domain.subscriber.DefaultSubscriber
import id.moviec.domain.subscriber.ResultState
import id.moviec.home.utils.HomeHolderType
import id.moviec.utils.constant.Const
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val getPopularMovies: GetPopularMovies,
    private val getDiscoverMovie: GetDiscoverMovie,
    private val getTopRatedMovies: GetTopRatedMovies,
    private val getNowPlayingMovies: GetNowPlayingMovies
): BaseViewModel() {

    private val _popularMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val popularMovies: LiveData<ResultState<List<MovieModel>?>> = _popularMovies
    fun fetchPopularMovie() {
        getPopularMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _popularMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _popularMovies.value = data
                }
            }, GetPopularMovies.Params(Const.API_KEY)
        )
    }

    private val _topRatedMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val topRatedMovies: LiveData<ResultState<List<MovieModel>?>> = _topRatedMovies
    fun fetchTopRatedMovie() {
        getTopRatedMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _topRatedMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success TopRated Fetched : $list")
                    _topRatedMovies.value = data
                }
            }, GetTopRatedMovies.Params(Const.API_KEY)
        )
    }

    private val _nowPlayingMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val nowPlayingMovies: LiveData<ResultState<List<MovieModel>?>> = _nowPlayingMovies

    fun fetchNowPlayingMovie() {
        getNowPlayingMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _nowPlayingMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success NowPlaying Fetched : $list")
                    _nowPlayingMovies.value = data
                }
            }, GetNowPlayingMovies.Params(Const.API_KEY)
        )
    }

    private val _discoverMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val discoverMovies: LiveData<ResultState<List<MovieModel>?>> = _discoverMovies
    fun fetchDiscoverMovies() {
        getDiscoverMovie.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _discoverMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success NowPlaying Fetched : $list")
                    _discoverMovies.value = data
                }
            }, GetDiscoverMovie.Params(Const.API_KEY)
        )
    }

    override fun onCleared() {
        getPopularMovies.dispose()
        getNowPlayingMovies.dispose()
        getTopRatedMovies.dispose()
        super.onCleared()
    }

    companion object {
        private const val TAG = "HomeViewModel"
    }

}