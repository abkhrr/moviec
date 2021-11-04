package id.moviec.utils.ext

import android.app.Activity
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Handler
import android.os.Looper
import android.view.View
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import androidx.annotation.AnimRes
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

fun Activity.hideKeyboard() {
    hideKeyboard(currentFocus ?: View(this))
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Context?.loadAnimation(@AnimRes resId: Int): Animation = AnimationUtils.loadAnimation(this, resId)

val Fragment.isNetworkAvailable: Boolean
    get() {
        return context?.isNetworkAvailable.orFalse()
    }

inline val Context.inputManager: InputMethodManager?
    get() {
        return ContextCompat.getSystemService(this, InputMethodManager::class.java)
    }

inline val Context.isNetworkAvailable: Boolean
    get() {
        val manager = ContextCompat.getSystemService(this, ConnectivityManager::class.java)
        val capabilities = manager?.getNetworkCapabilities(manager.activeNetwork)
        if (capabilities != null) {
            when {
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> return true
                capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> return true
            }
        }
        return false
    }

inline fun <T> T.applyIf(predicate: Boolean, block: T.() -> Unit): T = apply {
    if (predicate) block(this)
}

fun launchDelayedFunction(timeMillis: Long = 3000, action: () -> Unit) {
    Handler(Looper.getMainLooper()).postDelayed({ action() }, timeMillis)
}

fun forceClose() {
    android.os.Process.killProcess(android.os.Process.myPid())
}