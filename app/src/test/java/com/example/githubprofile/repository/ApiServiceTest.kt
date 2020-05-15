package com.example.githubprofile.repository

import okhttp3.OkHttpClient
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.IOException
import java.util.concurrent.TimeUnit


@RunWith(JUnit4::class)
class ApiServiceTest {
    private var apiService: ApiServices? = null
    private lateinit var query: String
    private var page :Int =0
    private var itemsPerPage:Int = 0

    @Before
    fun createService() {
        query ="aks08"
        page=1
        itemsPerPage=10
        val okHttpClient = OkHttpClient.Builder()
        okHttpClient.readTimeout(30, TimeUnit.SECONDS);
        okHttpClient.writeTimeout(30, TimeUnit.SECONDS);
        apiService = Retrofit.Builder()
            .baseUrl("https://api.github.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttpClient.build())
            .build().create(ApiServices::class.java)
    }

    @Test
    fun fetch() {
        try {
            val response: Response<*> = apiService!!.searchRepos(query,page,itemsPerPage).execute()
            assertEquals(response.code(), 200)
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}