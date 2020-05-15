package com.example.githubprofile.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.example.githubprofile.repository.db.GithubLocalCache
import com.example.githubprofile.repository.model.UserRepositories
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

/**
 * Repository class to deliver the data to view model class
 */
class GithubRepository(private val cache: GithubLocalCache) {
    /**
     * Search repos based on a query.
     * Trigger a request to the Github searchRepo API with the following params:
     * @param page request page index
     * @param itemsPerPage number of repositories to be returned by the Github API per page
     *
     * The result of the request is handled by the implementation of the functions passed as params
     * @param onSuccess function that defines how to handle the list of repos received
     * @param onError function that defines how to handle request failure
     */
    fun searchRepos(
        query: String,
        page: Int,
        itemsPerPage: Int,
        onSuccess: (repos: List<com.example.githubprofile.repository.model.Response>) -> Unit,
        onError: (error: String) -> Unit
    ) {
      //  Log.d(TAG, "query: $query, page: $page, itemsPerPage: $itemsPerPage")

        val apiQuery = query

        RetrofitClient.INSTANCE.apiService.searchRepos( query,page, itemsPerPage).enqueue(
            object : Callback<List<com.example.githubprofile.repository.model.Response>> {
                override fun onFailure(call: Call<List<com.example.githubprofile.repository.model.Response>>?, t: Throwable) {
                    Log.d(TAG, "fail to get data")
                    onError(t.message ?: "unknown error")
                }

                override fun onResponse(
                    call: Call<List<com.example.githubprofile.repository.model.Response>>?,
                    response: Response<List<com.example.githubprofile.repository.model.Response>>
                ) {
                    Log.d(TAG, "got a response $response")
                    if (response.isSuccessful) {
                        val repos = response.body() ?: emptyList()
                        onSuccess(repos)
                    } else {
                        onError(response.errorBody()?.string() ?: "Unknown error")
                    }
                }
            }
        )
    }

    // keep the last requested page. When the request is successful, increment the page number.
    private var lastRequestedPage = 1

    // LiveData of network errors.
    private val networkErrors = MutableLiveData<String>()

    // avoid triggering multiple requests in the same time
    private var isRequestInProgress = false

    /**
     * Search repositories whose names match the query.
     */
    fun search(query: String): UserRepositories {
        Log.d("GithubRepository", "New query: $query")
        lastRequestedPage = 1
        requestAndSaveData(query)

        // Get data from the local cache
        val data = cache.reposByName(query)

        return UserRepositories(data, networkErrors)
    }

    fun requestMore(query: String) {
        requestAndSaveData(query)
    }

    private fun requestAndSaveData(query: String) {
        if (isRequestInProgress) return

        isRequestInProgress = true
        searchRepos( query, lastRequestedPage, NETWORK_PAGE_SIZE, { repos ->
            cache.insert(repos) {
                lastRequestedPage++
                isRequestInProgress = false
            }
        }, { error ->
            networkErrors.postValue(error)
            isRequestInProgress = false
        })
    }


    companion object {
        private const val TAG = "GithubRepository"
        private const val NETWORK_PAGE_SIZE = 10
    }

}