package id.moviec

import androidx.appcompat.app.AppCompatDelegate
import dagger.hilt.android.HiltAndroidApp
import id.moviec.base.BaseApplication

@HiltAndroidApp
class MoviecApplication: BaseApplication() {
    override fun getBaseUrl(): String {
        return BuildConfig.BASE_URL
    }

    override fun onCreate() {
        super.onCreate()
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
    }
}