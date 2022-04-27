package com.example.movietestapplication.activity

import android.app.AlertDialog
import android.app.ProgressDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.movietestapplication.ApiClient
import com.example.movietestapplication.CustomAdapter
import com.example.movietestapplication.Model
import com.example.movietestapplication.R
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var recyclerView: RecyclerView
    private lateinit var manager: RecyclerView.LayoutManager
    private lateinit var customAdapter: RecyclerView.Adapter<*>
    private lateinit var progressDialog: ProgressDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val actionbar = supportActionBar
        //set actionbar title
        actionbar!!.title = "Movie List"

        progressDialog = ProgressDialog(this)
        progressDialog.setTitle("Movie Application")
        progressDialog.setMessage("Data Loading, please wait")
        progressDialog.show()
        if (checkForInternet(this)) {
            manager = LinearLayoutManager(this)
            getMovieListData()
        } else {
            /*Toast.makeText(
                this,
                "Close the application and check your internet connection.",
                Toast.LENGTH_SHORT
            ).show()*/
            progressDialog.dismiss()
            val builder = AlertDialog.Builder(this)
            builder.setMessage("Close the application and Check your Internet Connection")
            builder.setPositiveButton("OK") { dialog, which ->
                dialog.cancel()
                finish()
            }
            builder.setNegativeButton("Cancel") { dialog, which ->
                dialog.cancel()
            }
            val dialog: AlertDialog = builder.create()
            dialog.show()
        }
    }

    private fun checkForInternet(context: Context): Boolean {

        // register activity with the connectivity manager service
        val connectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

        // if the android version is equal to M
        // or greater we need to use the
        // NetworkCapabilities to check what type of
        // network has the internet connection
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {

            // Returns a Network object corresponding to
            // the currently active default data network.
            val network = connectivityManager.activeNetwork ?: return false

            // Representation of the capabilities of an active network.
            val activeNetwork = connectivityManager.getNetworkCapabilities(network) ?: return false

            return when {
                // Indicates this network uses a Wi-Fi transport,
                // or WiFi has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true

                // Indicates this network uses a Cellular transport. or
                // Cellular has network connectivity
                activeNetwork.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true

                // else return false
                else -> false
            }
        } else {
            // if the android version is below M
            @Suppress("DEPRECATION") val networkInfo =
                connectivityManager.activeNetworkInfo ?: return false
            @Suppress("DEPRECATION")
            return networkInfo.isConnected
        }
    }

    /**
     * API call to fetch list of movies
     */
    private fun getMovieListData() {
        ApiClient.getClient.getMovieNameData("c9856d0cb57c3f14bf75bdc6c063b8f3")
            .enqueue(object : Callback<Model> {
                override fun onResponse(
                    call: Call<Model>,
                    response: Response<Model>
                ) {
                    if (response.isSuccessful) {
                        println("Success: $response")
                        recyclerView = findViewById<RecyclerView>(R.id.recyclerView).apply {
                            progressDialog.dismiss()
                            customAdapter = CustomAdapter(response.body()?.results!!,
                                CustomAdapter.OnClickListener {
                                    val intent = Intent(context, MovieDetail::class.java)
                                    intent.putExtra("id", it.id)
                                    intent.putExtra("movie_name", it.title)
                                    intent.putExtra("movie_year", it.release_date.substring(0, 4))
                                    intent.putExtra("movie_detail", it.overview)
                                    intent.putExtra("movie_poster", it.poster_path)
                                    startActivity(intent)
                                })
                            layoutManager = manager
                            adapter = customAdapter
                        }
                    }
                }

                override fun onFailure(call: Call<Model>, t: Throwable) {
                    println("failure")
                    t.printStackTrace()
                }
            })
    }
}
