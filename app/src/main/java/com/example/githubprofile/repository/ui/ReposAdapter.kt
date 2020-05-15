

package com.example.githubprofile.repository.ui

import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import com.example.githubprofile.repository.model.Response

/**
 * Adapter for the list of repositories.
 */
class ReposAdapter : ListAdapter<Response, androidx.recyclerview.widget.RecyclerView.ViewHolder>(
    REPO_COMPARATOR
) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): androidx.recyclerview.widget.RecyclerView.ViewHolder {
        return RepoViewHolder.create(
            parent
        )
    }

    override fun onBindViewHolder(holder: androidx.recyclerview.widget.RecyclerView.ViewHolder, position: Int) {
        val repoItem = getItem(position)
        if (repoItem != null) {
            (holder as RepoViewHolder).bind(repoItem)
        }
    }

    companion object {
        private val REPO_COMPARATOR = object : DiffUtil.ItemCallback<Response>() {
            override fun areItemsTheSame(oldItem: Response, newItem: Response): Boolean =
                    oldItem.name == newItem.name

            override fun areContentsTheSame(oldItem: Response, newItem: Response): Boolean =
                    oldItem == newItem
        }
    }
}
