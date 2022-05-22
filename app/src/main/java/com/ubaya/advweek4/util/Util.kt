package com.ubaya.advweek4.util

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.util.Log
import android.view.View
import android.widget.ImageView
import android.widget.ProgressBar
import androidx.databinding.BindingAdapter
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso
import com.ubaya.advweek4.R
import java.lang.Exception

fun ImageView.loadImage(url: String, progressBar: ProgressBar){
    Picasso.get()
        .load(url)
        .resize(400, 400)
        .centerCrop()
        .error(R.drawable.ic_baseline_error_24)
        .into(this, object:Callback{
            override fun onSuccess() {
                progressBar.visibility = View.GONE
            }

            override fun onError(e: Exception?) { }
        })
}

@BindingAdapter("android:imageUrl", "android:progressBar")
fun loadPhotoUrl(view: ImageView, url:String?, pb: ProgressBar){
    if (url != null) view.loadImage(url!!, pb)
}

fun createNotificationChannel(context: Context, importance: Int, showBadge: Boolean, name: String, description:String){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
        val channelId = "${context.packageName}-$name"
        val channel = NotificationChannel(channelId, name, importance).apply {
            this.description = description
            setShowBadge(showBadge)
        }
        val notificationManager = context.getSystemService(NotificationManager::class.java)
        notificationManager.createNotificationChannel(channel)
    }
}