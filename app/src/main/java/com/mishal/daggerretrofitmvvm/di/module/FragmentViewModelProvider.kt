package com.mishal.daggerretrofitmvvm.di.module

import com.mishal.daggerretrofitmvvm.rest.RepoRepository
import com.mishal.daggerretrofitmvvm.viewModels.MainViewModel
import dagger.Module
import dagger.Provides

@Module
class FragmentViewModelProvider {

    @Provides
    fun providesListRepoFragmentModule(repository: RepoRepository): MainViewModel {
        return MainViewModel(repository)
    }

}