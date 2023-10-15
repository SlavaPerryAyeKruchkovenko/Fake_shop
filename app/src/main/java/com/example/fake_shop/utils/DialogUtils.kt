package com.example.fake_shop.utils

import android.app.Dialog
import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.View
import androidx.core.content.ContextCompat
import com.example.fake_shop.R
import com.example.fake_shop.databinding.DialogNotifyCreaterBinding
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

    fun createNotifyDialog(context: Context, binding: DialogNotifyCreaterBinding): Dialog {
        val myDialog = Dialog(context).apply {
            setContentView(binding.root)
            setCancelable(true)
            window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        }
        myDialog.show()
        return myDialog
    }
}