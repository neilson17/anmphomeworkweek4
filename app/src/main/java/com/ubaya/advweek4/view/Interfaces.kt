package com.ubaya.advweek4.view

import android.view.View
import com.ubaya.advweek4.model.Student

interface ButtonDetailClickListener{
    fun onDetailClick(view: View)
}

interface StudentNotificationClickListener{
    fun onNotificationClick(view: View, obj: Student)
}