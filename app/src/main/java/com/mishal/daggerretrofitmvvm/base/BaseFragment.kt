package com.mishal.daggerretrofitmvvm.base

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.LayoutRes
import androidx.databinding.DataBindingUtil
import androidx.databinding.ViewDataBinding
import androidx.lifecycle.ViewModel
import com.google.gson.Gson
import com.mishal.daggerretrofitmvvm.utils.PD
import com.mishal.daggerretrofitmvvm.utils.PreferenceHelper
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.DaggerAppCompatActivity
import dagger.android.support.DaggerFragment
import javax.inject.Inject

abstract class BaseFragment<V : ViewDataBinding, M : ViewModel> : DaggerFragment() {

    @Inject
    protected lateinit var helper: PreferenceHelper

    private lateinit var mViewDataBinding: V
    private lateinit var pd: PD

    private var mViewModel: M? = null
    private var mRootView: View? = null
    var baseActivity: BaseActivity<*, *>? = null
        private set

    val isNetworkConnected: Boolean
        get() = baseActivity != null && baseActivity!!.isNetworkConnected()


    /**
     * @return layout resource id
     */
    @LayoutRes
    abstract fun findContentView(): Int

    /**
     * Override for set binding variable
     *
     * @return variable id
     */
    abstract fun getBindingVariable(): Int

    fun getViewDataBinding(): V {
        return mViewDataBinding
    }

    abstract fun getViewModel(): M

    override fun onCreate(savedInstanceState: Bundle?) {
        performDependencyInjection()
        super.onCreate(savedInstanceState)
        mViewModel = getViewModel()
//        helper = PreferenceHelper(baseActivity!!, Prefs.PREF_FILE)
        setHasOptionsMenu(false)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mViewDataBinding = DataBindingUtil.inflate(inflater, findContentView(), container, false)
        mRootView = mViewDataBinding.root
        pd = PD(baseActivity!!)
        return mRootView
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mViewDataBinding.setVariable(getBindingVariable(), mViewModel)
        mViewDataBinding.executePendingBindings()
        onReady()

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is BaseActivity<*, *>) {
            val activity = context as BaseActivity<*, *>?
            this.baseActivity = activity
        }
    }

    override fun onDetach() {
        baseActivity = null
        super.onDetach()
    }

    private fun performDependencyInjection() {
        AndroidSupportInjection.inject(this)
    }

    abstract fun onReady()

    private val TAG = BaseFragment::class.java.simpleName

}