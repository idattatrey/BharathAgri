package com.bharath.agri.ui.adapters

import android.annotation.SuppressLint
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bharath.agri.R
import com.bharath.agri.data.local.tables.PopularMovie
import com.bharath.agri.ui.activities.DetailsActivity
import com.bumptech.glide.Glide

class MoviesAdapter(
    private val myDataset: List<PopularMovie>
) :
    RecyclerView.Adapter<MoviesAdapter.MovieViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): MovieViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_list_item, parent, false)
        return MovieViewHolder(view!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        holder.mTitleView!!.text = myDataset[position].title
        Glide.with(holder.itemView)
            .load("https://image.tmdb.org/t/p/w185/" + myDataset[position].poster_path)
            .placeholder(R.drawable.cinema)
            .error(R.drawable.cinema)
            .into(holder.mPosterView!!)
        holder.mRatingView!!.text = "${myDataset[position].vote_average}/10"

        holder.itemView.setOnClickListener {
            val detailsIntent = Intent(holder.itemView.context, DetailsActivity::class.java)
            detailsIntent.putExtra("Id", myDataset[position].id)
            holder.itemView.context.startActivity(detailsIntent)
        }
    }

    override fun getItemCount() = myDataset.size

    class MovieViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var mTitleView: TextView? = null
        var mPosterView: ImageView? = null
        var mRatingView: TextView? = null

        init {
            mTitleView = itemView.findViewById(R.id.title)
            mPosterView = itemView.findViewById(R.id.poster)
            mRatingView = itemView.findViewById(R.id.rating)
        }

    }
}

