package com.bharath.agri.data.remote.service

import com.bharath.agri.data.remote.model.casts.CastCrewDetails
import com.bharath.agri.data.remote.model.movie_details.MovieDetails
import com.bharath.agri.data.remote.model.popular_movies.PopularMoviesResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Path

interface ApiService {
    @GET("3/movie/popular?api_key=c3d6c5ca60b75f82b7817997022f988d")
    fun listOfPopularMovies(): Single<PopularMoviesResponse>

    @GET("3/movie/{movie_id}?api_key=c3d6c5ca60b75f82b7817997022f988d")
    fun getMovieDetails(@Path("movie_id") movie_id: Int): Single<MovieDetails>

    @GET("3/movie/{movie_id}/credits?api_key=c3d6c5ca60b75f82b7817997022f988d")
    fun getCastandCrew(@Path("movie_id") movie_id: Int): Single<CastCrewDetails>
}