package com.bharath.agri.ui.activities

import android.annotation.SuppressLint
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bharath.agri.R
import com.bharath.agri.constants.Constants
import com.bharath.agri.di.root.BharathAgriApp
import com.bharath.agri.ui.adapters.CastsAdapter
import com.bharath.agri.ui.adapters.CrewsAdapter
import com.bharath.agri.viewmodel.ViewModelProviderFactory
import com.bharath.agri.viewmodel.details_viewmodel.DetailsViewModel
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import kotlinx.android.synthetic.main.activity_details.*
import javax.inject.Inject


class DetailsActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: ViewModelProviderFactory

    private val detailsViewModel: DetailsViewModel by viewModels { mainViewModelFactory }

    private lateinit var castRecyclerView: RecyclerView
    private lateinit var castViewAdapter: RecyclerView.Adapter<*>
    private lateinit var castViewManager: RecyclerView.LayoutManager

    private lateinit var crewRecyclerView: RecyclerView
    private lateinit var crewViewAdapter: RecyclerView.Adapter<*>
    private lateinit var crewViewManager: RecyclerView.LayoutManager

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_details)

        (application as BharathAgriApp).appComponent.inject(this)

        detailsViewModel.movieId = intent.getIntExtra("Id", 0)

        if (Constants.isOnline(applicationContext)) {
            populateDataFromApi()
        } else {
            populateDataFromDb()
        }

        detailsViewModel.detailsResponse.observe(this, { it ->
            if (it != null) {
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + it.backdrop_path)
                    .placeholder(R.drawable.cinema_banner)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(movieBanner)

                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185/" + it.poster_path)
                    .placeholder(R.drawable.cinema_1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(moviePoster)

                movieName.text = it.title
                releaseStatus.text = it.status
                releaseDate.text = it.release_date
                duration.text = "${it.runtime} Mins"
                genre.text = it.genres.joinToString(separator = ",") { it.name }
                language.text = it.spoken_languages.joinToString(separator = "-") { it.name }

                imdb.setOnClickListener { _ ->
                    val url = "https://www.imdb.com/title/" + it.imdb_id
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

                overview.text = it.overview
            }

        })

        detailsViewModel.detailsResponseFromDb.observe(this, {
            if (it != null) {
                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w500/" + it.backdrop_path)
                    .placeholder(R.drawable.cinema_banner)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(movieBanner)

                Glide.with(this)
                    .load("https://image.tmdb.org/t/p/w185/" + it.poster_path)
                    .placeholder(R.drawable.cinema_1)
                    .diskCacheStrategy(DiskCacheStrategy.NONE)
                    .into(moviePoster)

                movieName.text = it.title
                releaseStatus.text = it.status
                releaseDate.text = it.release_date
                duration.text = "${it.time} Mins"
                genre.text = it.genre
                language.text = it.language

                imdb.setOnClickListener { _ ->
                    val url = "https://www.imdb.com/title/" + it.imdb_id
                    val i = Intent(Intent.ACTION_VIEW)
                    i.data = Uri.parse(url)
                    startActivity(i)
                }

                overview.text = it.overview
            }

        })

        detailsViewModel.castDetail.observe(this, {
            if (it != null && it.isNotEmpty()) {
                castViewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                castViewAdapter = CastsAdapter(it, null, true)

                castRecyclerView = findViewById<RecyclerView>(R.id.castRecyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = castViewManager
                    adapter = castViewAdapter
                }

                castViewAdapter.notifyDataSetChanged()

            }
        })

        detailsViewModel.castDetailFromDb.observe(this, {
            if (it != null && it.isNotEmpty()) {
                castViewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                castViewAdapter = CastsAdapter(null, it, false)

                castRecyclerView = findViewById<RecyclerView>(R.id.castRecyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = castViewManager
                    adapter = castViewAdapter
                }

                castViewAdapter.notifyDataSetChanged()
            }
        })

        detailsViewModel.crewDetail.observe(this, {
            if (it != null && it.isNotEmpty()) {
                crewViewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                crewViewAdapter = CrewsAdapter(it, null, true)

                crewRecyclerView = findViewById<RecyclerView>(R.id.crewRecyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = crewViewManager
                    adapter = crewViewAdapter
                }

                crewViewAdapter.notifyDataSetChanged()
            }
        })

        detailsViewModel.crewDetailFromDb.observe(this, {
            if (it != null && it.isNotEmpty()) {
                crewViewManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
                crewViewAdapter = CrewsAdapter(null, it, false)

                crewRecyclerView = findViewById<RecyclerView>(R.id.crewRecyclerView).apply {
                    setHasFixedSize(true)
                    layoutManager = crewViewManager
                    adapter = crewViewAdapter
                }

                crewViewAdapter.notifyDataSetChanged()
            }
        })
    }

    private fun populateDataFromApi() {
        detailsViewModel.getMovieDetails()
        detailsViewModel.getCastAndCrewDetails()
    }

    private fun populateDataFromDb() {
        detailsViewModel.getMovieDetailFromDb()
        detailsViewModel.getCastDetailsFromDb()
        detailsViewModel.getCrewDetailsFromDb()
    }

}