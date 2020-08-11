package com.mishal.daggerretrofitmvvm.di.module

import com.mishal.daggerretrofitmvvm.rest.RepoRepository
import com.mishal.daggerretrofitmvvm.viewModels.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class MainActivityModule {
    @Provides
    fun providesMainActivityModule(repository: RepoRepository): MainViewModel {
        return MainViewModel(repository)
    }

}