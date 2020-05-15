
package com.example.githubprofile.repository.db

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubprofile.repository.model.Response

/**
 * Room data access object for accessing the [Repo] table.
 */
@Dao
interface RepoDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(posts: List<Response>)


    // Look for user repos that contain the query string in the name
    @Query("SELECT * FROM repos  WHERE (owner LIKE :queryString)")
    fun reposByName(queryString: String): LiveData<List<Response>>
}
