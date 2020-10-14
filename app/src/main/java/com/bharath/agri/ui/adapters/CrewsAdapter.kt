package com.bharath.agri.ui.adapters

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bharath.agri.R
import com.bharath.agri.data.local.tables.MovieCrew
import com.bharath.agri.data.remote.model.casts.Crew
import com.bumptech.glide.Glide

class CrewsAdapter(
    private val myDataset: List<Crew>?,
    private val myLocalDataset: List<MovieCrew>?,
    private val checkFlag: Boolean
) :
    RecyclerView.Adapter<CrewsAdapter.CrewsViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): CrewsViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.crew_list_item, parent, false)
        return CrewsViewHolder(view!!)
    }

    @SuppressLint("SetTextI18n")
    override fun onBindViewHolder(holder: CrewsViewHolder, position: Int) {
        if (checkFlag) {
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w185/" + myDataset!![position].profile_path)
                .placeholder(R.drawable.cinema)
                .into(holder.mCrewImageView!!)
            holder.mCrewName!!.text = myDataset[position].name
            holder.mCrewJob!!.text = myDataset[position].job
        } else {
            Glide.with(holder.itemView)
                .load("https://image.tmdb.org/t/p/w185/" + myLocalDataset!![position].profile_path)
                .placeholder(R.drawable.cinema)
                .into(holder.mCrewImageView!!)
            holder.mCrewName!!.text = myLocalDataset[position].cast_name
            holder.mCrewJob!!.text = myLocalDataset[position].job
        }
    }

    override fun getItemCount() = if (checkFlag) {
        myDataset!!.size
    } else {
        myLocalDataset!!.size
    }

    class CrewsViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {

        var mCrewImageView: ImageView? = null
        var mCrewName: TextView? = null
        var mCrewJob: TextView? = null

        init {
            mCrewImageView = itemView.findViewById(R.id.crewImage)
            mCrewName = itemView.findViewById(R.id.crewName)
            mCrewJob = itemView.findViewById(R.id.crewJob)
        }

    }
}

