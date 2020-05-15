

package com.example.githubprofile.repository.db

import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import android.content.Context
import androidx.room.TypeConverters
import com.example.githubprofile.repository.model.Response
import com.example.githubprofile.repository.model.User
import com.example.githubprofile.repository.model.UserConverters

/**
 * Database schema that holds the list of repos.
 */
@Database(
        entities = [Response::class],
        version = 1,
        exportSchema = false
)
@TypeConverters(UserConverters::class)
abstract class RepoDatabase : RoomDatabase() {

    abstract fun reposDao(): RepoDao

    companion object {

        @Volatile
        private var INSTANCE: RepoDatabase? = null

        fun getInstance(context: Context): RepoDatabase =
                INSTANCE ?: synchronized(this) {
                    INSTANCE
                            ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        RepoDatabase::class.java, "GithubUser.db")
                        .build()
    }
}
