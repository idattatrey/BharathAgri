package com.bharath.agri.viewmodel.details_viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bharath.agri.data.local.room.mapToDB
import com.bharath.agri.data.local.tables.MovieCast
import com.bharath.agri.data.local.tables.MovieCrew
import com.bharath.agri.data.local.tables.MovieDetail
import com.bharath.agri.data.remote.model.casts.Cast
import com.bharath.agri.data.remote.model.casts.CastCrewDetails
import com.bharath.agri.data.remote.model.casts.Crew
import com.bharath.agri.data.remote.model.movie_details.MovieDetails
import com.bharath.agri.di.repository.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class DetailsViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    var detailsResponse =
        MutableLiveData<MovieDetails>()
    var detailsResponseFromDb =
        MutableLiveData<MovieDetail>()


    var castDetail =
        MutableLiveData<List<Cast>>()
    var castDetailFromDb =
        MutableLiveData<List<MovieCast>>()

    var crewDetail =
        MutableLiveData<List<Crew>>()
    var crewDetailFromDb =
        MutableLiveData<List<MovieCrew>>()

    var movieId: Int = 0

    fun getMovieDetails() {
        val repos = appRepository.getRemoteDataSource().getMovieDetails(movieId)

        disposables.add(
            repos
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<MovieDetails>() {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(response: MovieDetails) {
                        detailsResponse.value = response
                        insertMovieDetailIntoDb(response)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("List Error", e.toString())
                    }
                })
        )
    }


    fun getCastAndCrewDetails() {
        val repos = appRepository.getRemoteDataSource().getCastandCrew(movieId)

        disposables.add(
            repos
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<CastCrewDetails>() {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(response: CastCrewDetails) {
                        castDetail.value = response.cast
                        crewDetail.value = response.crew

                        insertCastIntoDb(response.cast)
                        insertCrewIntoDb(response.crew)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("List Error", e.toString())
                    }
                })
        )
    }

    private fun insertMovieDetailIntoDb(response: MovieDetails) {
        val genre = response.genres.joinToString(separator = ",") { it.name }
        val language =
            response.spoken_languages.joinToString(separator = "-") { it.name }
        disposables.add(
            appRepository.getRoomDao()
                .addMovieDetail(
                    MovieDetail(
                        movie_id = response.id,
                        backdrop_path = response.backdrop_path,
                        genre = genre,
                        imdb_id = response.imdb_id,
                        original_title = response.original_title,
                        title = response.title,
                        overview = response.overview,
                        poster_path = response.poster_path,
                        language = language,
                        vote_average = response.vote_average,
                        time = response.runtime,
                        release_date = response.release_date,
                        status = response.status
                    )
                )
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("Inserted Detail", "True")
                }, {
                    Log.d("Inserted Detail Failed", it.message.toString())
                })
        )
    }

    private fun insertCastIntoDb(list: List<Cast>) {
        disposables.add(
            appRepository.getRoomDao()
                .addAllCast(list.map { it.mapToDB(movieId) } as ArrayList<MovieCast>)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("Inserted Cast", "True")
                }, {
                    Log.d("Inserted Cast Failed", it.message.toString())
                })
        )
    }

    private fun insertCrewIntoDb(list: List<Crew>) {
        disposables.add(
            appRepository.getRoomDao()
                .addAllCrew(list.map { it.mapToDB(movieId) } as ArrayList<MovieCrew>)
                .subscribeOn(Schedulers.io())
                .subscribe({
                    Log.d("Inserted Crew", "True")
                }, {
                    Log.d("Inserted Crew Failed", it.message.toString())
                })
        )
    }

    fun getMovieDetailFromDb() {
        disposables.add(
            appRepository.getRoomDao().getMovieDetail(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    detailsResponseFromDb.value = it
                }, {
                    detailsResponseFromDb.value = null
                })
        )
    }

    fun getCastDetailsFromDb() {
        disposables.add(
            appRepository.getRoomDao().getAllCastsFromMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    castDetailFromDb.value = it
                }, {
                    castDetailFromDb.value = null
                })
        )
    }

    fun getCrewDetailsFromDb() {
        disposables.add(
            appRepository.getRoomDao().getAllCrewsFromMovie(movieId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    crewDetailFromDb.value = it
                }, {
                    crewDetailFromDb.value = null
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}