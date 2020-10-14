package com.bharath.agri.di.root

import android.app.Application
import com.bharath.agri.di.component.AppComponent
import com.bharath.agri.di.component.DaggerAppComponent

class BharathAgriApp : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder().application(this)?.build()!!
    }
}