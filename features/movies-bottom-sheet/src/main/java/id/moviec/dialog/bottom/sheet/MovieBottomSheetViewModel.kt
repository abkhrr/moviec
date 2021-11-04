package id.moviec.dialog.bottom.sheet

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import id.moviec.base.viewmodel.BaseViewModel
import id.moviec.domain.entities.DetailMovieModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.interactors.DeleteFavoriteMovie
import id.moviec.domain.interactors.GetDetailMovie
import id.moviec.domain.interactors.GetFavoriteMovie
import id.moviec.domain.interactors.InsertFavoriteMovie
import id.moviec.domain.subscriber.DefaultSubscriber
import id.moviec.domain.subscriber.ResultState
import id.moviec.utils.constant.Const
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MovieBottomSheetViewModel @Inject constructor(
    private val insertFavoriteMovie: InsertFavoriteMovie,
    private val deleteFavoriteMovie: DeleteFavoriteMovie,
    private val getFavoriteMovie: GetFavoriteMovie,
    private val getDetailMovie: GetDetailMovie,
): BaseViewModel() {

    private val _getFavoriteMovie = MutableLiveData<ResultState<MovieModel?>>()
    val getFavoriteMovieLiveData: LiveData<ResultState<MovieModel?>> = _getFavoriteMovie
    fun fetchFavoriteMovie(movieId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            getFavoriteMovie.execute(
                object : DefaultSubscriber<MovieModel?>() {
                    override fun onError(error: ResultState<MovieModel?>) {
                        val message: String? = (error as ResultState.Error).throwable.message
                        Log.d(TAG, "Error : $message")
                        _getFavoriteMovie.value = error
                    }

                    override fun onSuccess(data: ResultState<MovieModel?>) {
                        val list = (data as ResultState.Success).data
                        Log.d(TAG, "Success Popular Fetched : $list")
                        _getFavoriteMovie.value = data
                    }
                }, GetFavoriteMovie.Params(movieId)
            )
        }
    }

    private val _insertMovie = MutableLiveData<ResultState<Long>>()
    val insertMovieLiveData: LiveData<ResultState<Long>> = _insertMovie
    fun insertFavoriteMovie(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            insertFavoriteMovie.execute(object : DefaultSubscriber<Long>() {

                override fun onError(error: ResultState<Long>) {
                    _insertMovie.postValue(error)
                }

                override fun onSuccess(data: ResultState<Long>) {
                    _insertMovie.postValue(data)
                }
            }, InsertFavoriteMovie.Params(movieModel))
        }
    }

    private val _deleteMovie = MutableLiveData<ResultState<Int>>()
    val deleteMovieLiveData: LiveData<ResultState<Int>> = _deleteMovie
    fun deleteFavoriteMovie(movieModel: MovieModel) {
        viewModelScope.launch(Dispatchers.IO) {
            deleteFavoriteMovie.execute(object : DefaultSubscriber<Int>() {

                override fun onError(error: ResultState<Int>) {
                    _deleteMovie.postValue(error)
                }

                override fun onSuccess(data: ResultState<Int>) {
                    _deleteMovie.postValue(data)
                }
            }, DeleteFavoriteMovie.Params(movieModel))
        }
    }

    private val _getDetailMovie = MutableLiveData<ResultState<DetailMovieModel>>()
    val getDetailMovieLiveData: LiveData<ResultState<DetailMovieModel>> = _getDetailMovie
    fun fetchDetailMovie(movieId: Int) {
        getDetailMovie.execute(
            object : DefaultSubscriber<DetailMovieModel>() {
                override fun onError(error: ResultState<DetailMovieModel>) {
                    val message: String? = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _getDetailMovie.value = error
                }

                override fun onSuccess(data: ResultState<DetailMovieModel>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _getDetailMovie.value = data
                }
            }, GetDetailMovie.Params(Const.API_KEY, movieId)
        )
    }

    companion object {
        private const val TAG = "MovieBottomSheetViewModel"
    }

}