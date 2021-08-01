package com.darkmoose117.gather.util

import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AlertDialog
import com.darkmoose117.gather.R

fun Context.openUrlDialog(url: String) {
    AlertDialog.Builder(this)
        .setTitle(R.string.open_url_title)
        .setMessage(getString(R.string.open_url_message, url))
        .setCancelable(true)
        .setPositiveButton(R.string.ok) { dialog, _ ->
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
            dialog.dismiss()
        }.setNegativeButton(R.string.cancel) { dialog, _ ->
            dialog.dismiss()
        }.show()
}