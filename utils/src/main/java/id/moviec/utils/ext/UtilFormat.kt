package id.moviec.utils.ext

import android.content.res.Resources
import androidx.lifecycle.MutableLiveData
import java.text.DecimalFormat

fun Int.dpToPx(): Int {
    return this * Resources.getSystem().displayMetrics.density.toInt()
}
fun Int?.orZero():Int = this ?: 0
fun Int?.orOne(): Int = this ?: 1
fun Int?.plus(value :Int?):Int {
    if(this == null) return 0
    if(value == null) return this
    return this + value
}

fun Boolean?.orTrue(): Boolean = this ?: true
fun Boolean?.orFalse(): Boolean = this ?: false

fun Double?.orZero(): Double = this ?: 0.0
fun Double?.orOne(): Double = this ?: 1.0

fun Long?.orZero(): Long = this ?: 0L
fun Long?.orOne(): Long = this ?: 1L

fun Float.getDistanceMerchant(): String? {
    return DecimalFormat("#.##").format(this)
}
fun Float?.orZero(): Float = this ?: 0f
fun Float?.orOne(): Float = this ?: 1f

fun String.getAtIndex(index: Int): String {
    return try {
        get(index).toString()
    } catch (e: Exception) {
        ""
    }
}
fun String?.toIntOrZero(): Int {
    return this?.filter { it.isDigit() }?.toIntOrNull() ?: 0
}
fun String?.isLetters(): Boolean = orEmpty().replace(" ", "").matches("^[a-zA-Z]*$".toRegex()).orFalse()
fun String?.isNumeric(): Boolean = orEmpty().matches("[0-9]+".toRegex()).orFalse()

fun MutableLiveData<String>.get(index: Int): String {
    return try {
        (value?.get(index) ?: "").toString()
    } catch (e: Exception) {
        ""
    }
}