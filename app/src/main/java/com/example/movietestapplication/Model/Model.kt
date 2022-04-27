package com.example.movietestapplication

import com.google.gson.annotations.SerializedName

data class Model(
    val page: Int,
    val total_pages: Int,
    val total_results: Int,
    val results: List<Results>
) {
    data class Results(
        val adult: Boolean,
        val backdrop_path: String,
        val id: Int,
        val original_title: String,
        val overview: String,
        val release_date: String,
        val poster_path: String,
        val title: String
    )
}