
package com.example.githubprofile.repository.utils

import android.content.Context
import androidx.lifecycle.ViewModelProvider
import com.example.githubprofile.repository.GithubRepository
import com.example.githubprofile.repository.db.GithubLocalCache
import com.example.githubprofile.repository.db.RepoDatabase
import com.example.githubprofile.repository.viewmodel.ViewModelFactory
import java.util.concurrent.Executors

/**
 * This class handles object creation.
 * Similarly, objects can be passed as parameters in the constructors and then replaced for
 * testing, where needed.
 */
object Injection {

    /**
     * Creates an instance of [GithubLocalCache] based on the database DAO.
     */
    private fun provideCache(context: Context): GithubLocalCache {
        val database = RepoDatabase.getInstance(context)
        return GithubLocalCache(database.reposDao(), Executors.newSingleThreadExecutor())
    }

    /**
     * Creates an instance of [GithubRepository] based on the
     * [GithubLocalCache]
     */
    private fun provideGithubRepository(context: Context): GithubRepository {
        return GithubRepository(
            provideCache(
                context
            )
        )
    }

    /**
     * Provides the [ViewModelProvider.Factory] that is then used to get a reference to
     * [ViewModel] objects.
     */
    fun provideViewModelFactory(context: Context): ViewModelProvider.Factory {
        return ViewModelFactory(
            provideGithubRepository(
                context
            )
        )
    }
}


