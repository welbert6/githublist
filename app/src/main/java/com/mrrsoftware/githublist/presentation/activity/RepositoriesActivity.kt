package com.mrrsoftware.githublist.presentation.activity

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ActivityRepositoriesBinding
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.presentation.adapter.RepositoriesAdapter
import com.mrrsoftware.githublist.presentation.state.RepositoriesState
import com.mrrsoftware.githublist.presentation.viewmodel.RepositoriesListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesActivity : AppCompatActivity() {

    private val binding: ActivityRepositoriesBinding by lazy {
        ActivityRepositoriesBinding.inflate(layoutInflater)
    }

    private val viewModel: RepositoriesListViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupViewModel()
        setupRecycleView()
    }

    private fun setupRecycleView() {
        binding.rcRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val totalItemCount = layoutManager.itemCount
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (totalItemCount <= (lastVisibleItem + LOAD_WHEN_CLOSE_ITEMS_TO_FINISH)) {
                    viewModel.fetchRepositories()
                }
            }
        })


        binding.rcRepos.adapter = RepositoriesAdapter()

    }

    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    is RepositoriesState.ShowLoading -> {
                        binding.loading.visibility = View.VISIBLE
                    }

                    is RepositoriesState.ShowRepositories -> {
                        showRepositories(state.list)

                    }

                    is RepositoriesState.ShowError -> {
                        showError()
                    }

                    is RepositoriesState.HideLoading -> {
                        binding.loading.visibility = View.GONE
                    }


                }
            }
        }

        viewModel.fetchRepositories()

    }

    private fun showError() {
        Toast.makeText(this, getString(R.string.error_generico_api), Toast.LENGTH_LONG).show()
    }

    private fun showRepositories(list: List<Repository>) {
        (binding.rcRepos.adapter as RepositoriesAdapter).submitList(list)
    }

    companion object {
        private val LOAD_WHEN_CLOSE_ITEMS_TO_FINISH = 5
    }
}