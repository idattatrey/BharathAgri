package com.bharath.agri.data.local.room

import android.opengl.ETC1.encodeImage
import android.util.Base64
import com.bharath.agri.data.local.tables.MovieCast
import com.bharath.agri.data.local.tables.MovieCrew
import com.bharath.agri.data.local.tables.PopularMovie
import com.bharath.agri.data.remote.model.casts.Cast
import com.bharath.agri.data.remote.model.casts.Crew
import java.io.FileInputStream
import java.net.URL


fun com.bharath.agri.data.remote.model.popular_movies.Result.mapToDB(): PopularMovie {
    return PopularMovie(
        this.id,
        this.popularity,
        this.vote_count,
        this.poster_path,
        this.backdrop_path,
        this.original_language,
        this.original_title,
        this.title,
        this.vote_average,
        this.overview,
        this.release_date
    )
}

fun Cast.mapToDB(movieId: Int): MovieCast {
    return MovieCast(
        movie_id = movieId,
        cast_name = this.name,
        profile_path = this.profile_path
    )
}

fun Crew.mapToDB(movieId: Int): MovieCrew {
    return MovieCrew(
        movie_id = movieId,
        cast_name = this.name,
        profile_path = this.profile_path,
        job = this.job
    )
}

