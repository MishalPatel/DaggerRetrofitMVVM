package com.mishal.daggerretrofitmvvm

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mishal.daggerretrofitmvvm.adapter.ListRepoAdapter
import com.mishal.daggerretrofitmvvm.base.BaseActivity
import com.mishal.daggerretrofitmvvm.databinding.ActivityMainBinding
import com.mishal.daggerretrofitmvvm.model.Repo
import com.mishal.daggerretrofitmvvm.rest.RepoRepository
import com.mishal.daggerretrofitmvvm.utils.NetworkUtils
import com.mishal.daggerretrofitmvvm.viewModels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : BaseActivity<ActivityMainBinding, MainViewModel>() {

    override fun getBindingVariable(): Int {
        return BR.viewModel
    }

    @Inject
    lateinit var vm: MainViewModel

    @Inject
    lateinit var context: MyApp

    override fun getViewModel(): MainViewModel {
        return vm
    }

    override fun findContentView(): Int {
        return R.layout.activity_main
    }

    private lateinit var adapter: ListRepoAdapter
    lateinit var binding: ActivityMainBinding
    override fun onReady(savedInstanceState: Bundle?) {
        binding = getViewDataBinding()

        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = ListRepoAdapter()
        binding.rvList.adapter = adapter
        observeListRepoViewModel()
    }

    private fun observeListRepoViewModel() {
        pd.createDialog()
        pd.show()
        vm.getRepos().observe(this, Observer<List<Repo>>() {
            if (it != null) {
                pd.hide()
//                binding.setVariable(BR.isError, false)
//                binding.setVariable(BR.isLoading, false)
                adapter.addPostData(it as ArrayList<Repo>)
                /*  adapter.setCustomClickListener(object : ListRepoAdapter.CustomClickListener {
                  override fun onClick(view: View, position: Int) {
                      Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_LONG)
                          .show()
                  }

              })*/
                Log.d("SIZE", "${it.size}")
            }
        })

        vm.getRepoLoadError().observe(this, Observer<Boolean>() {
            if (it != null) {
                pd.hide()
//                binding.setVariable(BR.isError, it)
//                binding.setVariable(BR.isLoading, false)
            }
        })

        vm.getLoading().observe(this, Observer<Boolean>() {
            if (it != null) {
//                binding.setVariable(BR.isError, false)
//                binding.setVariable(BR.isLoading, true)
            }
        })

    }
}