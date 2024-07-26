package com.mrrsoftware.githublist.presentation.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import com.mrrsoftware.githublist.databinding.FragmentPullRequestBinding
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.REPOSITORY_ID

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PullRequestsFragment : Fragment() {

    private val binding: FragmentPullRequestBinding by lazy {
        FragmentPullRequestBinding.inflate(layoutInflater)
    }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    }


    companion object {
        fun newInstance(repoId: String): PullRequestsFragment {
            return PullRequestsFragment().apply {
                arguments = bundleOf(REPOSITORY_ID to repoId)
            }
        }
    }
}