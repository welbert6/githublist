package com.mrrsoftware.githublist.presentation.adapter

/**
 * Created by Welbert on 25/07/2024
 */

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import android.view.View
import android.widget.TextView
import com.bumptech.glide.Glide
import com.google.android.material.imageview.ShapeableImageView
import com.mrrsoftware.githublist.R
import com.mrrsoftware.githublist.databinding.ItemRepositoryBinding
import com.mrrsoftware.githublist.domain.entity.Repository

class RepositoriesAdapter : RecyclerView.Adapter<RepositoriesAdapter.RepositoriesViewHolder>() {

    private var repositories: MutableList<Repository> = mutableListOf()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RepositoriesViewHolder {
        val binding = ItemRepositoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return RepositoriesViewHolder(binding.root)
    }

    override fun onBindViewHolder(holder: RepositoriesViewHolder, position: Int) {
        val repository = repositories[position]
        with(holder){
            titleTextView.text = repository.title
            descriptionTextView.text = repository.description
            usernameTextView.text = repository.user.userName
            txtCountStart.text = repository.starsCount.toString()
            txtCountPullRequests.text = repository.pullRequestsCount.toString()
            Glide.with(this.itemView)
                .load(repository.user.imageUrl)
                .placeholder(R.drawable.baseline_account_circle_24)
                .error(R.drawable.baseline_account_circle_24)
                .into(userImageView)
        }

    }

    override fun getItemCount(): Int = repositories.size

    fun submitList(newRepositories: List<Repository>) {
        val startRangeChange = repositories.size
        repositories.addAll(newRepositories)
        notifyItemRangeInserted(startRangeChange,repositories.size)
    }

    inner class RepositoriesViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.txtTitleNameRepository)
        val descriptionTextView: TextView = itemView.findViewById(R.id.txtDescriptionRepository)
        val usernameTextView: TextView = itemView.findViewById(R.id.txtUsername)
        val userImageView: ShapeableImageView = itemView.findViewById(R.id.imageViewUserRepository)
        val txtCountPullRequests: TextView = itemView.findViewById(R.id.txtCountPullRequests)
        val txtCountStart: TextView = itemView.findViewById(R.id.txtCountStart)
    }

}

