package com.mishal.daggerretrofitmvvm.base

import androidx.databinding.ObservableBoolean
import androidx.lifecycle.AndroidViewModel
import com.mishal.daggerretrofitmvvm.MyApp
import io.reactivex.disposables.CompositeDisposable
import java.lang.ref.WeakReference


abstract class BaseViewModel (application: MyApp?) : AndroidViewModel(application!!) {

    val isLoading = ObservableBoolean(false)

    protected var compositeDisposable: CompositeDisposable = CompositeDisposable()


    protected fun setIsLoading(isLoading: Boolean) {
        this.isLoading.set(isLoading)
    }

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }
}
