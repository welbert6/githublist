package com.mrrsoftware.githublist.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ActivityPullRequestsBinding

class PullRequestsActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityPullRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPullRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_content_pull_requests)
                ?: return

        val navController = navHostFragment.findNavController()
        appBarConfiguration = AppBarConfiguration(navController.graph)
        setupActionBarWithNavController(navController, appBarConfiguration)

        val repositoryId = intent.getStringExtra(REPOSITORY_ID)
        val fragment = repositoryId?.let { PullRequestsFragment.newInstance(it) }
        fragment?.let {
            supportFragmentManager.beginTransaction()
                .replace(R.id.nav_host_fragment_content_pull_requests, it)
                .commit()
        }
    }

    companion object {
        const val REPOSITORY_ID = "repoId"
    }

}