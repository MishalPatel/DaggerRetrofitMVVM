package com.mishal.daggerretrofitmvvm.di.component

import com.mishal.daggerretrofitmvvm.di.module.ActivityModule
import com.mishal.daggerretrofitmvvm.di.module.AppModule
import com.mishal.daggerretrofitmvvm.di.module.ContextModule
import com.mishal.daggerretrofitmvvm.di.module.FragmentModule
import com.mishal.daggerretrofitmvvm.MyApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import dagger.android.support.DaggerApplication
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        ContextModule::class,
        FragmentModule::class
    ]
)
interface AppComponent : AndroidInjector<DaggerApplication> {

    fun inject(instance: MyApp)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(daggerApplication: DaggerApplication): Builder

        fun build(): AppComponent
    }
}