package com.mishal.daggerretrofitmvvm

import com.mishal.daggerretrofitmvvm.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.support.DaggerApplication

class MyApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        val application = DaggerAppComponent.builder().application(this).build()
        application.inject(this)
        return application
    }

}