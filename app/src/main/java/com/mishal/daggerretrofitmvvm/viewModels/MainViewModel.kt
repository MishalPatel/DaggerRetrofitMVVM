package com.mishal.daggerretrofitmvvm.viewModels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.mishal.daggerretrofitmvvm.MyApp
import com.mishal.daggerretrofitmvvm.base.BaseViewModel
import com.mishal.daggerretrofitmvvm.rest.RepoRepository
import com.mishal.daggerretrofitmvvm.model.Repo
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject


class MainViewModel @Inject constructor(private val repoRepository: RepoRepository) :
    BaseViewModel(MyApp.getInstance()) {

    var repos = MutableLiveData<List<Repo>>()
    var repoLoadError = MutableLiveData<Boolean>()
    var loading = MutableLiveData<Boolean>()


    init {
        compositeDisposable = CompositeDisposable()
        fetchRepos()
    }

    fun getRepos(): LiveData<List<Repo>> {
        return repos
    }

    fun getLoading(): LiveData<Boolean> {
        return loading
    }

    fun getRepoLoadError(): LiveData<Boolean> {
        return repoLoadError
    }

    fun fetchRepos() {
        loading.value = true
        val repoDisposable = repoRepository
            .getRepositories()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribeWith(object : DisposableSingleObserver<List<Repo>>() {
                override fun onSuccess(t: List<Repo>) {
                    repos.value = t
                    loading.value = false
                    repoLoadError.value = false
                }

                override fun onError(e: Throwable) {
                    repoLoadError.value = true
                    loading.value = false
                }
            })
        compositeDisposable.add(repoDisposable)
    }

    override fun onCleared() {
        super.onCleared()
        compositeDisposable.clear()
//        compositeDisposable = null
    }
}