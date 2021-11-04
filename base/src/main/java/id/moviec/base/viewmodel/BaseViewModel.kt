package id.moviec.base.viewmodel

import androidx.lifecycle.ViewModel
import id.moviec.utils.event.SingleLiveEvent

abstract class BaseViewModel: ViewModel() {
    val isLoading: SingleLiveEvent<Boolean> = SingleLiveEvent()
    val showToast: SingleLiveEvent<String>  = SingleLiveEvent()
    val showSnack: SingleLiveEvent<String>  = SingleLiveEvent()

    fun onComplete() {
    }

    fun onDataEmpty() {
    }

    fun resume() {
    }

    fun pause() {
    }

    fun destroy() {
    }
}