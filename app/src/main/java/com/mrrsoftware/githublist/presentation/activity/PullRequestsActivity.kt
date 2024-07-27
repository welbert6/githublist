package com.mrrsoftware.githublist.presentation.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ActivityPullRequestsBinding
import com.mrrsoftware.githublist.presentation.fragments.PullRequestsFragment

class PullRequestsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityPullRequestsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityPullRequestsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)


        initFragment()

    }

    private fun initFragment() {
        val repoName = intent.getStringExtra(REPOSITORY) ?: ""
        val ownerName = intent.getStringExtra(OWNER) ?: ""
        val issues = intent.getIntExtra(ISSUES,0)
        PullRequestsFragment.newInstance(ownerName,repoName,issues).apply {
            supportFragmentManager.beginTransaction()
                .add(R.id.container, this)
                .commit()
        }
    }

    companion object {
        const val REPOSITORY = "repo"
        const val OWNER = "owner"
        const val ISSUES = "issues"


    }

}