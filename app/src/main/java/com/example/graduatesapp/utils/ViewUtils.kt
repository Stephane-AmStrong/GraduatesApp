package com.example.graduatesapp.utils

import android.app.Activity
import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.OpenableColumns
import android.telephony.TelephonyManager
import android.view.View
import android.widget.ProgressBar
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.graduatesapp.data.network.Resource
import com.google.android.material.snackbar.Snackbar
import java.text.DecimalFormatSymbols
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.*


fun Context.toast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT ).show()
}

fun Fragment.toast(message: String){
    Toast.makeText(this.requireContext(), message, Toast.LENGTH_SHORT ).show()
}

fun ProgressBar.show(){
    visibility = View.VISIBLE
}

fun ProgressBar.hide(){
    visibility = View.GONE
}

fun View.snackbar(message: String){
    Snackbar.make(this, message, Snackbar.LENGTH_SHORT).also { snackbar ->
        snackbar.setAction("Ok") {
            snackbar.dismiss()
        }
    }.show()
}


fun View.snackbarLengthIndefinite(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_INDEFINITE)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun View.snackbar(message: String, action: (() -> Unit)? = null) {
    val snackbar = Snackbar.make(this, message, Snackbar.LENGTH_LONG)
    action?.let {
        snackbar.setAction("Retry") {
            it()
        }
    }
    snackbar.show()
}

fun Fragment.handleApiError(
    failure: Resource.Failure,
    retry: (() -> Unit)? = null
) {
    when {
        failure.isNetworkError -> requireView().snackbar(
            "Veuillez vérifier votre accès au réseau",
            retry
        )
        /*
        failure.errorCode == 401 -> {
            if (this is LoginFragment) {
                requireView().snackbar("You've entered incorrect email or password")
            } else {
                (this as BaseFragment<*, *, *>).logout()
            }
        }
        */
        else -> {
            val error = failure.errorBody?.string().toString()
            requireView().snackbarLengthIndefinite(error)
        }
    }
}

///////:


fun <A : Activity> Activity.startNewActivity(activity: Class<A>){
    Intent(this, activity).also {
        it.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        startActivity(it)
    }
}

fun <A : Activity> Activity.startActivity(activity: Class<A>){
    Intent(this, activity).also {
        startActivity(it)
    }
}

fun View.visible(isVisible: Boolean) {
    visibility = if (isVisible) View.VISIBLE else View.GONE
}

fun View.enable(enabled: Boolean) {
    isEnabled = enabled
    alpha = if (enabled) 1f else 0.5f
}

fun Int.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val current: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun Float.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val current: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun Long.toDecimalFormat() : String{
    return NumberFormat.getInstance(Locale.FRENCH).format(this)
//    val current: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}

fun String.reverseDecimalFormat(): String {
    return this.replace(DecimalFormatSymbols(Locale.FRENCH).groupingSeparator.toString().toRegex(), "")
}


fun Date.formatToPattern(pattern: String = "d MMM yyyy HH:mm:ss") : String{
    val sdfDate = SimpleDateFormat(pattern, Locale.getDefault())

    return sdfDate.format(this)
//    val current: Locale = getResources().getConfiguration().locale
//    return NumberFormat.getInstance(Locale.getDefault()).format(this)
}


fun Context.runningOnTablet() : Boolean{
    val manager = applicationContext.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
    return (Objects.requireNonNull(manager).phoneType == TelephonyManager.PHONE_TYPE_NONE)
}

//
//fun List<LineItem>.toLineItemWithTaxes(): List<LineItemWithTaxes> {
//    return this.map {
//        LineItemWithTaxes(it, it.line_taxes)
//    }
//}
//
//
//fun List<LineItemWithTaxes>.toLineItem(): List<LineItem> {
//    return this.map {
//        LineItem(it.lineItem, it.lineTaxes)
//    }
//}
//
//fun LineItemWithTaxes.toLineItem(): LineItem {
//    return LineItem(this.lineItem, this.qte.toFloat(), this.lineTaxes)
//}
//
//fun LineItem.toLineItemWithTaxes(): LineItemWithTaxes {
//    return LineItemWithTaxes(this, this.line_taxes)
//}



inline fun <T> Iterable<T>.sumByLong(selector: (T) -> Long): Long {
    var sum = 0L
    for (element in this) {
        sum += selector(element)
    }
    return sum
}


inline fun <T> Iterable<T>.sumByFloat(selector: (T) -> Float): Float {
    var sum = 0F
    for (element in this) {
        sum += selector(element)
    }
    return sum
}




fun ContentResolver.getFileName(fileUri: Uri): String {
    var name = ""
    val returnCursor = this.query(fileUri, null, null, null, null)
    if (returnCursor != null) {
        val nameIndex = returnCursor.getColumnIndex(OpenableColumns.DISPLAY_NAME)
        returnCursor.moveToFirst()
        name = returnCursor.getString(nameIndex)
        returnCursor.close()
    }
    return name
}