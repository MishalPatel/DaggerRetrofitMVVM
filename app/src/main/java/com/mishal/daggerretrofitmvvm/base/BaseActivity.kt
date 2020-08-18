package com.mishal.daggerretrofitmvvm.base

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.widget.Toast
import androidx.annotation.LayoutRes
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mishal.daggerretrofitmvvm.R
import com.mishal.daggerretrofitmvvm.utils.NetworkUtils
import com.mishal.daggerretrofitmvvm.utils.PD
import com.mishal.daggerretrofitmvvm.utils.PreferenceHelper
import dagger.Provides
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

abstract class BaseActivity<V : ViewDataBinding, M : BaseViewModel> : DaggerAppCompatActivity() {

    private val TAG = BaseActivity::class.java.simpleName

    protected lateinit var mActivity: AppCompatActivity

    @Inject
    protected lateinit var helper: PreferenceHelper

    protected lateinit var pd: PD
    private var mViewModel: M? = null
    private lateinit var mViewDataBinding: V


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mActivity = this

        mViewDataBinding = DataBindingUtil.setContentView(this, findContentView())
        pd = PD(mActivity)


        this.mViewModel = if (mViewModel == null) getViewModel() else mViewModel
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()

        onReady(savedInstanceState)
    }

    abstract fun getBindingVariable(): Int
    fun getViewDataBinding(): V {
        return mViewDataBinding
    }

    /**
     * Override for set view model
     *
     * @return view model instance
     */
    abstract fun getViewModel(): M

    fun isNetworkConnected(): Boolean {
        return NetworkUtils.isNetworkConnected(applicationContext)
    }

    abstract fun onReady(savedInstanceState: Bundle?)

    @LayoutRes
    abstract fun findContentView(): Int


    fun showToast(msg: String) {
        Handler(Looper.getMainLooper())
            .post { Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show() }
    }

    fun showToast(@StringRes msg: Int) {
        Handler(Looper.getMainLooper())
            .post { Toast.makeText(mActivity, msg, Toast.LENGTH_LONG).show() }
    }

    fun showUToast(error1: MutableList<Error>) {
        val error = Gson().toJson(error1).toString()
        Handler(Looper.getMainLooper())
            .post {
                Toast.makeText(
                    mActivity,
                    mActivity.resources.getString(R.string.msg_unexpected_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun showUToast(exception: Exception) {
        Handler(Looper.getMainLooper())
            .post {
                Toast.makeText(
                    mActivity,
                    mActivity.resources.getString(R.string.msg_unexpected_error),
                    Toast.LENGTH_SHORT
                ).show()
            }
    }

    fun showError() {
        Handler(Looper.getMainLooper())
            .post {
                Toast.makeText(
                    mActivity,
                    mActivity.resources.getString(R.string.msg_internet_connection),
                    Toast.LENGTH_LONG
                ).show()
            }
    }

}