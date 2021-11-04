package id.moviec.utils.navigation

import android.content.Context
import android.content.Intent

fun sendActivityIntent(context: Context, action: String) = Intent(action).setPackage(context.packageName)