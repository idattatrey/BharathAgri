package com.bharath.agri.viewmodel.main_viewmodel

import android.annotation.SuppressLint
import android.util.Log
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bharath.agri.data.local.room.mapToDB
import com.bharath.agri.data.local.tables.PopularMovie
import com.bharath.agri.data.remote.model.popular_movies.PopularMoviesResponse
import com.bharath.agri.di.repository.AppRepository
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val appRepository: AppRepository
) : ViewModel() {
    private val disposables = CompositeDisposable()
    var responseList =
        MutableLiveData<List<PopularMovie>>()

    fun getPopularMovieList() {
        val repos = appRepository.getRemoteDataSource().listOfPopularMovies()

        disposables.add(
            repos
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<PopularMoviesResponse>() {
                    @SuppressLint("CheckResult")
                    override fun onSuccess(response: PopularMoviesResponse) {
                        val theResponse =
                            response.results.map { it.mapToDB() } as ArrayList<PopularMovie>
                        responseList.value = theResponse
                        insertResponseIntoDb(theResponse)
                    }

                    override fun onError(e: Throwable) {
                        Log.i("List Error", e.toString())
                    }
                })
        )
    }

    private fun insertResponseIntoDb(list: ArrayList<PopularMovie>) {
        disposables.add(
            appRepository.getRoomDao()
                .addAllMovies(list)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    Log.d("Inserted", "True")
                }, {
                    Log.d("Inserted failed", it.message.toString())
                })
        )
    }

    fun getPopularMovieListFromDb() {
        disposables.add(
            appRepository.getRoomDao().getAllMoviesDb()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    responseList.value = it
                }, {
                    responseList.value = null
                })
        )
    }

    override fun onCleared() {
        super.onCleared()
        disposables.clear()
    }

}