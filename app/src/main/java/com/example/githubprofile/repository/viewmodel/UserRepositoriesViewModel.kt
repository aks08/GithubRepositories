
package com.example.githubprofile.repository.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

import com.example.githubprofile.repository.GithubRepository
import com.example.githubprofile.repository.model.Response
import com.example.githubprofile.repository.model.UserRepositories

/**
 * ViewModel for the [UserRepositoriesActivity] screen.
 * The ViewModel works with the [GithubRepository] to get the data.
 */
class UserRepositoriesViewModel(private val repository: GithubRepository) : ViewModel() {

    companion object {
        private const val VISIBLE_THRESHOLD = 5
    }

    private val queryLiveData = MutableLiveData<String>()
    private val repoResult: LiveData<UserRepositories> = Transformations.map(queryLiveData) {
        repository.search(it)
    }

    val repos: LiveData<List<Response>> = Transformations.switchMap(repoResult) { it -> it.data }
    val networkErrors: LiveData<String> = Transformations.switchMap(repoResult) { it ->
        it.networkErrors
    }

    /**
     * Search a repository based on a query string.
     */
    fun searchRepo(queryString: String) {
        queryLiveData.postValue(queryString)
    }

    fun listScrolled(visibleItemCount: Int, lastVisibleItemPosition: Int, totalItemCount: Int) {
        if (visibleItemCount + lastVisibleItemPosition + VISIBLE_THRESHOLD >= totalItemCount) {
            val immutableQuery = lastQueryValue()
            if (immutableQuery != null) {
                repository.requestMore(immutableQuery)
            }
        }
    }

    /**
     * Get the last query value.
     */
    fun lastQueryValue(): String? = queryLiveData.value
}
