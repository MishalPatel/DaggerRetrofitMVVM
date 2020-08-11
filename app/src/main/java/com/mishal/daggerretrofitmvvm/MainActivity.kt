package com.mishal.daggerretrofitmvvm

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.mishal.daggerretrofitmvvm.adapter.ListRepoAdapter
import com.mishal.daggerretrofitmvvm.databinding.ActivityMainBinding
import com.mishal.daggerretrofitmvvm.model.Repo
import com.mishal.daggerretrofitmvvm.rest.RepoRepository
import com.mishal.daggerretrofitmvvm.viewModels.MainViewModel
import dagger.android.support.DaggerAppCompatActivity
import javax.inject.Inject

class MainActivity : DaggerAppCompatActivity(), ListRepoAdapter.SetOnItemClickListener {
    lateinit var binding: ActivityMainBinding


    @Inject
    lateinit var listRepoViewModel: MainViewModel
    private lateinit var adapter: ListRepoAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.rvList.layoutManager = LinearLayoutManager(this)
        binding.rvList.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        adapter = ListRepoAdapter()
        binding.rvList.adapter = adapter
        observeListRepoViewModel()
    }

    private fun observeListRepoViewModel() {
        listRepoViewModel.getRepos().observe(this, Observer<List<Repo>>() {
            if (it != null) {
                binding.setVariable(BR.isError, false)
                binding.setVariable(BR.isLoading, false)
                adapter.addPostData(it as ArrayList<Repo>)
                /*adapter.setCustomClickListener(object : ListRepoAdapter.CustomClickListener {
                    override fun onClick(view: View, position: Int) {
                        Toast.makeText(this@MainActivity, position.toString(), Toast.LENGTH_LONG)
                            .show()
                    }

                })*/
                Log.d("SIZE", "${it.size}")
            }
        })

        listRepoViewModel.getRepoLoadError().observe(this, Observer<Boolean>() {
            if (it != null) {
                binding.setVariable(BR.isError, it)
                binding.setVariable(BR.isLoading, false)
            }
        })

        listRepoViewModel.getLoading().observe(this, Observer<Boolean>() {
            if (it != null) {
                binding.setVariable(BR.isError, false)
                binding.setVariable(BR.isLoading, true)
            }
        })

    }

    override fun onItemClicked() {

    }

}