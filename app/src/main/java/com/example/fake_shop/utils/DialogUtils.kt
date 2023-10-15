package com.example.fake_shop.utils

import android.content.Context
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fake_shop.R
import com.google.android.material.snackbar.Snackbar

object DialogUtils {
    fun viewShackBar(context: Context, view: View, text: String, isError: Boolean = true) {
        val snackbar = Snackbar.make(
            view, text,
            Snackbar.LENGTH_LONG
        )
        snackbar.setBackgroundTint(
            ContextCompat.getColor(
                context, if (isError) {
                    R.color.red
                } else {
                    R.color.success
                }
            )
        )
        snackbar.setTextColor(
            ContextCompat.getColor(
                context, if (isError) {
                    R.color.white
                } else {
                    R.color.black
                }
            )
        )
        snackbar.show()
    }
}