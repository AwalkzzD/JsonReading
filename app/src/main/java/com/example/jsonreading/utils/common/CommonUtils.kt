package com.example.jsonreading.utils.common

import android.app.AlertDialog
import android.content.Context
import com.example.jsonreading.R

object CommonUtils {
    fun getProgressDialog(context: Context): AlertDialog {
        val dialogBuilder = AlertDialog.Builder(context)
        dialogBuilder.setView(R.layout.progress_dialog)
        dialogBuilder.setCancelable(false)
        return dialogBuilder.create()
            .apply { window?.setBackgroundDrawableResource(android.R.color.transparent) }
    }
}