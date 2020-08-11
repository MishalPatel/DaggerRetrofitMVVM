package com.mishal.daggerretrofitmvvm.di.module

import com.mishal.daggerretrofitmvvm.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {
    @ContributesAndroidInjector(modules = [MainActivityModule::class])
    abstract fun bindMainActivity(): MainActivity


}