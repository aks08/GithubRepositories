package com.example.githubprofile.repository.model

import androidx.lifecycle.LiveData

/**
 * UserRepositories from a search, which contains LiveData<List<Repo>> holding query data,
 * and a LiveData<String> of network error state.
 */
data class UserRepositories(
    val data: LiveData<List<Response>>,
    val networkErrors: LiveData<String>
)