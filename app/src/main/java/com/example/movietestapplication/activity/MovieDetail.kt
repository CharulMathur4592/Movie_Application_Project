package com.example.movietestapplication.activity

import android.app.ProgressDialog
import android.content.Intent
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.bitmap.CenterCrop
import com.example.movietestapplication.R

@Suppress("DEPRECATION")
class MovieDetail : AppCompatActivity() {

    private lateinit var movieNameTitle: TextView
    private lateinit var movieReleaseDate: TextView
    private lateinit var moviePosterImage: ImageView
    private lateinit var movieOverview: TextView
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.movie_detail)
        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Movie Application")
        progressDialog.setMessage("Data Loading, please wait")
        progressDialog.show()

        val actionBar = getSupportActionBar()
        actionBar!!.title = "Movie Details"
        actionBar.setDisplayHomeAsUpEnabled(true)

        movieNameTitle = findViewById(R.id.detail_movie_name)
        movieReleaseDate = findViewById(R.id.detail_movie_year)
        moviePosterImage = findViewById(R.id.movie_image)
        movieOverview = findViewById(R.id.detail_movie_detail)

        movieNameTitle.text = intent.getStringExtra("movie_name")
        movieReleaseDate.text = intent.getStringExtra("movie_year")
        movieOverview.text = intent.getStringExtra("movie_detail")

        val moviePoster = intent.getStringExtra("movie_poster")
        Glide.with(this)
            .load("https://image.tmdb.org/t/p/original$moviePoster")
            .transform(CenterCrop())
            .into(moviePosterImage)
        progressDialog.dismiss()
    }

    /**
     * to handle the back pressed
     */
    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}