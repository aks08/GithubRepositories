package com.example.githubprofile.repository

import com.example.githubprofile.repository.model.Response
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Api to hit with respect to query
 */
interface ApiServices {

    @GET("users/{query}/repos")
    fun searchRepos(
        @Path("query") user:String ,
        @Query("page") page: Int,
        @Query("per_page") itemsPerPage: Int
    ): Call<List<Response>>
}