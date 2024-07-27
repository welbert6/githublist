package com.mrrsoftware.githublist.presentation.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ItemPullRequestBinding
import com.mrrsoftware.githublist.domain.entity.PullRequest

/**
 * Created by Welbert on 26/07/2024
 */

class PullRequestAdapter(
    private val list: List<PullRequest>
): RecyclerView.Adapter<PullRequestAdapter.PullRequestsViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PullRequestsViewHolder {
        val binding =
            ItemPullRequestBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return PullRequestsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: PullRequestsViewHolder, position: Int) {
        holder.bind(list[position])
    }


    override fun getItemCount(): Int = list.size

    inner class PullRequestsViewHolder(private val binding: ItemPullRequestBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(pullRequest : PullRequest) {
            binding.apply {
                txtTitleNameRepository.text = pullRequest.title
                txtDescriptionRepository.text = pullRequest.body
                txtUsername.text = pullRequest.user.userName
                Glide.with(itemView)
                    .load(pullRequest.user.imageUrl)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(imageViewUserRepository)
            }
        }
    }
}