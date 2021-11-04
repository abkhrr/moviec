package id.moviec.utils.navigation

import android.app.Activity
import id.moviec.utils.navigation.sendActivityIntent

fun Activity.startMainActivity(activity: Activity) {
    val intent = sendActivityIntent(activity, "com.food_dev.dashboard.activity.ui.MainActivity")
    activity.finish()
    startActivity(intent)
}

fun Activity.startAuthActivity(activity: Activity) {
    val intent = sendActivityIntent(activity, "com.food_dev.auth.activity.ui.AuthActivity")
    activity.finish()
    startActivity(intent)
}