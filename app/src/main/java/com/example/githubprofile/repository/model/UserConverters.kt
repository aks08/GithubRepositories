package com.example.githubprofile.repository.model

import androidx.room.TypeConverter
import com.google.gson.Gson

class UserConverters {
    private var gson = Gson()

    @TypeConverter
    fun toUser(string: String?): User? {
        return if (string == null)
            null
        else
            gson.fromJson(string, User::class.java)
    }

    @TypeConverter
    fun toString(source: User?): String? {
        return if (source == null)
            null
        else {
            gson.toJson(source)
        }
    }
}
