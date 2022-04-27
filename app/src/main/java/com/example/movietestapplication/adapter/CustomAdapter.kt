package com.example.movietestapplication

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide

class CustomAdapter(
    private val dataList: List<Model.Results>,
    private val onClickListener: OnClickListener
) :
    RecyclerView.Adapter<CustomAdapter.MyViewHolder>() {

    class MyViewHolder(var view: View) :
        RecyclerView.ViewHolder(view) {

        fun bind(model: Model.Results) {
            val movieName = view.findViewById<TextView>(R.id.movieName)
            val imageView = view.findViewById<ImageView>(R.id.imageView)
            val movieYear = view.findViewById<TextView>(R.id.movieYear)

            movieName.text = model.original_title
            movieYear.text = model.release_date.substring(0, 4)
            Glide.with(view.context).load("https://image.tmdb.org/t/p/w500" + model.poster_path)
                .centerCrop().into(imageView)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v =
            LayoutInflater.from(parent.context).inflate(R.layout.custom_card_view, parent, false)
        return MyViewHolder(v)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        holder.bind(dataList[position])
        holder.itemView.setOnClickListener {
            onClickListener.onClick(dataList[position])
        }
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    class OnClickListener(val clickListener: (model: Model.Results) -> Unit) {
        fun onClick(model: Model.Results) = clickListener(model)
    }
}