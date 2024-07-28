package com.mrrsoftware.githublist.presentation.activity

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.VisibleForTesting
import androidx.appcompat.app.AppCompatActivity
import androidx.core.os.bundleOf
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ActivityRepositoriesBinding
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.ISSUES
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.OWNER
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.REPOSITORY
import com.mrrsoftware.githublist.presentation.adapter.RepositoryAdapter
import com.mrrsoftware.githublist.presentation.state.RepositoriesState
import com.mrrsoftware.githublist.presentation.viewmodel.RepositoriesListViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

class RepositoriesActivity : AppCompatActivity() {

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val binding: ActivityRepositoriesBinding by lazy {
        ActivityRepositoriesBinding.inflate(layoutInflater)
    }

    private val viewModel: RepositoriesListViewModel by viewModel()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(binding.root)
        setupRecycleView()
        setupViewModel()
    }

    private fun setupRecycleView() {
        binding.rcRepos.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                val layoutManager = recyclerView.layoutManager as LinearLayoutManager
                val lastVisibleItem = layoutManager.findLastVisibleItemPosition()
                if (layoutManager.itemCount <= (lastVisibleItem + LOAD_WHEN_CLOSE_ITEMS_TO_FINISH)) {
                    viewModel.fetchRepositories()
                }
            }
        })


        binding.rcRepos.adapter = RepositoryAdapter { repository ->
            startActivity(
                Intent(this, PullRequestsActivity::class.java)
                    .putExtras(
                        bundleOf(
                            REPOSITORY to repository.title,
                            OWNER to repository.user.userName,
                            ISSUES to repository.pullRequestsCount
                        )
                    )

            )
        }

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
        (binding.rcRepos.adapter as RepositoryAdapter).submitList(list)
        announceLoadDoneAccessibility()

    }

    private fun announceLoadDoneAccessibility() {
        val manager = getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (manager.isEnabled) {
            val event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            event.text.add("Carregamento concluído")
            manager.sendAccessibilityEvent(event)
        }
    }

    companion object {
        private const val LOAD_WHEN_CLOSE_ITEMS_TO_FINISH = 5
    }
}