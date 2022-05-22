package com.ubaya.advweek4.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.ubaya.advweek4.R
import com.ubaya.advweek4.util.createNotificationChannel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.kotlin.Observables
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.TimeUnit

class MainActivity : AppCompatActivity() {
    companion object{
        private var instance:MainActivity? = null

        fun showNotification (title:String, content: String, icon: Int){
            val channelID = "${instance?.packageName}-${instance?.getString(R.string.app_name)}"
            instance?.let{
                val notificationBuilder = NotificationCompat.Builder(it.applicationContext, channelID).apply{
                    setSmallIcon(icon)
                    setContentTitle(title)
                    setContentText(content)
                    setStyle(NotificationCompat.BigTextStyle())
                    priority = NotificationCompat.PRIORITY_DEFAULT
                    setAutoCancel(true)
                }
                val notificationManager = NotificationManagerCompat.from(it.applicationContext)
                notificationManager.notify(1001, notificationBuilder.build())
            }
        }
    }
    init {
        instance = this // init itu kyk constructors
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        createNotificationChannel(this, NotificationManagerCompat.IMPORTANCE_DEFAULT, false, getString(R.string.app_name), "App Notification Channel.")

        val observable = Observable.just("A stream of data", "Hellow", "RxJava")
        val observer = object: Observer<String> {
            override fun onSubscribe(d: Disposable?) {
                Log.d("rxjava", "Begin Subscribe")
            }

            override fun onNext(t: String?) {
                Log.d("rxjava", t!!)
            }

            override fun onError(e: Throwable?) {
                Log.d("rxerror", e!!.message.toString())
            }

            override fun onComplete() {
                Log.d("rxjava", "Complete")
            }

        }

        observable
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(observer)

        Observable.just("Another stream of data", "Hello again", "RxKotlin")
            .subscribe(
                { Log.d("rxkotlin", it) },
                { Log.d("rxerror", it.message.toString()) },
                { Log.d("rxkotlin", "Finish!") }
            )

        Observable
            .timer(5, TimeUnit.SECONDS)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                Log.d("delay", "I am delayed by five seconds!")
            }
    }
}