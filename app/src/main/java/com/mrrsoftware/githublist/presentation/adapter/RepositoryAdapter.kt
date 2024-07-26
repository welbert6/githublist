package com.mrrsoftware.githublist.presentation.adapter

/**
 * Created by Welbert on 25/07/2024
 */

import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ItemRepositoryBinding
import com.mrrsoftware.githublist.domain.entity.Repository
import com.mrrsoftware.githublist.presentation.activity.PullRequestsActivity

class RepositoryAdapter : RecyclerView.Adapter<RepositoryAdapter.RepositoriesViewHolder>() {

    private var repositories: MutableList<Repository> = mutableListOf()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val binding =
            ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoriesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        holder.bind(repositories[position])
    }


    override fun getItemCount(): Int = repositories.size

    fun submitList(newRepositories: List<Repository>) {
        val oldList = ArrayList(repositories)
        repositories.addAll(newRepositories)
        val diffCallback = RepositoryDiffCallback(oldList, repositories)
        val diffResult = DiffUtil.calculateDiff(diffCallback)
        diffResult.dispatchUpdatesTo(this)
    }

    inner class RepositoriesViewHolder(private val binding: ItemRepositoryBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(repository: Repository) {
            binding.apply {
                txtTitleNameRepository.text = repository.title
                txtDescriptionRepository.text = repository.description
                txtUsername.text = repository.user.userName
                txtCountStart.text = repository.starsCount.toString()
                txtCountPullRequests.text = repository.pullRequestsCount.toString()
                Glide.with(itemView)
                    .load(repository.user.imageUrl)
                    .placeholder(R.drawable.baseline_account_circle_24)
                    .error(R.drawable.baseline_account_circle_24)
                    .into(imageViewUserRepository)

                root.setOnClickListener {
                    val context = it.context
                    val intent = Intent(context, PullRequestsActivity::class.java)
                    context.startActivity(intent)
                }
            }
        }
    }


    class RepositoryDiffCallback(
        private val oldList: List<Repository>,
        private val newList: List<Repository>
    ) : DiffUtil.Callback() {

        override fun getOldListSize(): Int = oldList.size

        override fun getNewListSize(): Int = newList.size

        override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition].id == newList[newItemPosition].id
        }

        override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
            return oldList[oldItemPosition] == newList[newItemPosition]
        }
    }
}

