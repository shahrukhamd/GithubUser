package com.shahrukhamd.githubuser.data.base

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.shahrukhamd.githubuser.data.model.GithubUser
import com.shahrukhamd.githubuser.data.model.GithubUserDao

/**
 * The Room database for this app
 */
@Database(
    entities = [GithubUser::class],
    version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun userItemDao(): GithubUserDao

    companion object {

        // For Singleton instantiation
        @Volatile
        private var instance: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {
            return instance ?: synchronized(this) {
                instance ?: buildDatabase(context).also { instance = it }
            }
        }

        private fun buildDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(context, AppDatabase::class.java, "github-user-db")
                .fallbackToDestructiveMigration()
                .build()
        }
    }
}