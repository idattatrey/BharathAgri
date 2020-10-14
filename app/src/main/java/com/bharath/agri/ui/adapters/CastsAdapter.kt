package com.bharath.agri.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bharath.agri.R
import com.bharath.agri.data.local.tables.MovieCast
import com.bharath.agri.data.remote.model.casts.Cast
import com.bumptech.glide.Glide

class CastsAdapter(
    private val myDataset: List<Cast>?,
    private val myLocalDataset: List<MovieCast>?,
    private val checkFlag: Boolean
) :
    RecyclerView.Adapter<CastsAdapter.CastsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CastsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.cast_list_item, parent, false)
        return CastsViewHolder(view!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CastsViewHolder, position: Int) {
        if (checkFlag) {
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w185/" + myDataset!![position].profile_path)
                .placeholder(R.drawable.cinema)
                .into(holder.mCastingImageView!!)
            holder.mCastingTextView!!.text = myDataset[position].name
        } else {
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w185/" + myLocalDataset!![position].profile_path)
                .placeholder(R.drawable.cinema)
                .into(holder.mCastingImageView!!)
            holder.mCastingTextView!!.text = myLocalDataset[position].cast_name
        }
    }

    override fun getItemCount() = if (checkFlag) {
        myDataset!!.size
    } else {
        myLocalDataset!!.size
    }

    class CastsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var mCastingImageView: ImageView? = null
        var mCastingTextView: TextView? = null

        init {
            mCastingImageView = itemView.findViewById(R.id.castingImage)
            mCastingTextView = itemView.findViewById(R.id.castingName)
        }

    }
}

