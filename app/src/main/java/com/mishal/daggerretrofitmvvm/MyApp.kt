package com.mishal.daggerretrofitmvvm

import android.app.Activity
import androidx.appcompat.app.AppCompatDelegate
import com.mishal.daggerretrofitmvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val application = DaggerAppComponent.builder().application(this).build()
        application.inject(this)
        return application
    }
    companion object {
        private var mInstance: MyApp? = null

        @Synchronized
        fun getInstance(): MyApp? {
            return mInstance
        }

    }
    operator fun get(activity: Activity): MyApp {
        return activity.application as MyApp
    }
    override fun onCreate() {
        super.onCreate()
        mInstance = this
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);

    }
}