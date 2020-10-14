package com.bharath.agri.ui.activities

import android.os.Bundle
import android.util.TypedValue
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.bharath.agri.R
import com.bharath.agri.constants.Constants
import com.bharath.agri.di.root.BharathAgriApp
import com.bharath.agri.ui.adapters.MoviesAdapter
import com.bharath.agri.ui.utils.SpacesItemDecorationBottom
import com.bharath.agri.viewmodel.ViewModelProviderFactory
import com.bharath.agri.viewmodel.main_viewmodel.MainViewModel
import kotlinx.android.synthetic.main.activity_main.*
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var mainViewModelFactory: ViewModelProviderFactory

    private val mainViewModel: MainViewModel by viewModels { mainViewModelFactory }

    private lateinit var recyclerView: RecyclerView
    private var moviesAdapter: MoviesAdapter? = null
    private lateinit var layoutManager: RecyclerView.LayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        (application as BharathAgriApp).appComponent.inject(this)

        val space = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            16f,
            resources.displayMetrics
        )

        setUp()

        swipeRefresh.setOnRefreshListener {
            if (swipeRefresh.isRefreshing) {
                swipeRefresh.isRefreshing = false
            }
            setUp()
        }

        mainViewModel.responseList.observe(this, {
            if (it.isNotEmpty()) {
                recyclerView = findViewById(R.id.movieList)
                recyclerView.setHasFixedSize(false)
                recyclerView.addItemDecoration(SpacesItemDecorationBottom(space.toInt()))

                layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
                recyclerView.layoutManager = layoutManager
                moviesAdapter = MoviesAdapter(it)
                recyclerView.adapter = moviesAdapter
                moviesAdapter!!.notifyDataSetChanged()
            } else {
                val builder = AlertDialog.Builder(this)
                builder.setTitle("Alert")
                builder.setMessage("You don't have any data to display!")
                builder.setPositiveButton("OK") { _, _ ->
                    finish()
                }
                builder.show()
            }
        })

    }

    private fun setUp() {
        if (Constants.isOnline(applicationContext)) {
            mainViewModel.getPopularMovieList()
        } else {
            mainViewModel.getPopularMovieListFromDb()
        }
    }
}