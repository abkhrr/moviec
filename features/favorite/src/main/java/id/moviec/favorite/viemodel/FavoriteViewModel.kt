package id.moviec.favorite.viemodel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import dagger.hilt.android.lifecycle.HiltViewModel
import id.moviec.base.viewmodel.BaseViewModel
import id.moviec.domain.entities.MovieModel
import id.moviec.domain.interactors.DeleteFavoriteMovie
import id.moviec.domain.interactors.GetDetailMovie
import id.moviec.domain.interactors.GetFavoriteMovie
import id.moviec.domain.interactors.GetFavoriteMovies
import id.moviec.domain.subscriber.DefaultSubscriber
import id.moviec.domain.subscriber.ResultState
import javax.inject.Inject
import javax.inject.Named

@HiltViewModel
class FavoriteViewModel @Inject constructor(
    private val getFavoriteMovies: GetFavoriteMovies
): BaseViewModel() {

    private val _favoriteMovies = MutableLiveData<ResultState<List<MovieModel>?>>()
    val favoriteMovies: LiveData<ResultState<List<MovieModel>?>> = _favoriteMovies

    fun fetchFavoriteMovies() {
        getFavoriteMovies.execute(
            object : DefaultSubscriber<List<MovieModel>?>() {
                override fun onError(error: ResultState<List<MovieModel>?>) {
                    val message = (error as ResultState.Error).throwable.message
                    Log.d(TAG, "Error : $message")
                    _favoriteMovies.value = error
                }

                override fun onSuccess(data: ResultState<List<MovieModel>?>) {
                    val list = (data as ResultState.Success).data
                    Log.d(TAG, "Success Popular Fetched : $list")
                    _favoriteMovies.value = data
                }
            }, null
        )
    }

    companion object {
        private const val TAG = "FavoriteViewModel"
    }

}