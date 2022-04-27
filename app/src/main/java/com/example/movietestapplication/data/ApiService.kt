package com.example.movietestapplication

import retrofit2.Call

import retrofit2.http.GET
import retrofit2.http.Query


interface ApiService {

    @GET("movie")
    fun getMovieNameData(@Query("api_key") key: String): Call<Model>

    @GET("movies/get-movie-details\n")
    fun getMovieDetailsData(): Call<List<Model>>
}