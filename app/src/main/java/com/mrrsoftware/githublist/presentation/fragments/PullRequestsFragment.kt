package com.mrrsoftware.githublist.presentation.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.accessibility.AccessibilityEvent
import android.view.accessibility.AccessibilityManager
import android.widget.Toast
import androidx.annotation.VisibleForTesting
import androidx.core.content.ContextCompat.getSystemService
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.FragmentPullRequestBinding
import com.mrrsoftware.githublist.domain.entity.PullRequest
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.ISSUES
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.OWNER
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity.Companion.REPOSITORY
import com.mrrsoftware.githublist.presentation.adapter.PullRequestAdapter
import com.mrrsoftware.githublist.presentation.state.PullRequestState
import com.mrrsoftware.githublist.presentation.viewmodel.PullRequestsViewModel
import kotlinx.coroutines.launch
import org.koin.androidx.viewmodel.ext.android.viewModel

/**
 * A simple [Fragment] subclass as the default destination in the navigation.
 */
class PullRequestsFragment : Fragment() {


    private val viewModel: PullRequestsViewModel by viewModel()

    private var _binding: FragmentPullRequestBinding? = null

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val binding get() = _binding!!


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentPullRequestBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupViewModel()
    }


    private fun setupViewModel() {
        lifecycleScope.launch {
            viewModel.state.collect { state ->
                when (state) {
                    PullRequestState.HideLoading -> hideLoading()
                    PullRequestState.ShowError -> showError()
                    PullRequestState.ShowLoading -> showLoading()
                    is PullRequestState.ShowPullRequests -> showPullRequests(state.list)
                    PullRequestState.ShowEmptyPullRequests -> showEmptyPullRequests()
                }
            }
        }

        fetchPullRequest()
    }

    private fun showEmptyPullRequests() {
        binding.txtTotalCount.text = "Nenhuma pull request aberta"
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    private fun fetchPullRequest(){
        val ownerName = arguments?.getString(OWNER) ?: return showError()
        val repoName = arguments?.getString(REPOSITORY) ?: return showError()

        viewModel.fetchPullRequests(
            ownerName,
            repoName
        )
    }

    private fun showLoading() {
        binding.loading.visibility = View.VISIBLE
    }

    private fun hideLoading() {
        binding.loading.visibility = View.GONE
    }

    private fun showPullRequests(list: List<PullRequest>) {
        binding.txtTotalCount.text = getString(
            R.string.message_total_and_opened_issues,
            list.size,
            arguments?.getInt(ISSUES, 0)
        )
        binding.rcPulls.adapter = PullRequestAdapter(list)
        announceLoadDoneAccessibility()

    }

    private fun showError() {
        Toast.makeText(requireActivity(), getString(R.string.error_generico_api), Toast.LENGTH_LONG)
            .show()
    }

    private fun announceLoadDoneAccessibility() {
        val manager = requireActivity().getSystemService(Context.ACCESSIBILITY_SERVICE) as AccessibilityManager
        if (manager.isEnabled) {
            val event = AccessibilityEvent.obtain(AccessibilityEvent.TYPE_ANNOUNCEMENT)
            event.text.add("Carregamento concluído")
            manager.sendAccessibilityEvent(event)
        }
    }


    companion object {
        fun newInstance(
            ownerName: String,
            repoName: String,
            issuesCount: Int
        ): PullRequestsFragment {
            return PullRequestsFragment().apply {
                arguments = bundleOf(
                    REPOSITORY to repoName,
                    OWNER to ownerName,
                    ISSUES to issuesCount
                )
            }
        }
    }
}