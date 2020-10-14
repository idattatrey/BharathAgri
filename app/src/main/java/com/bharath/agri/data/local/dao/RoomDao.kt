package com.bharath.agri.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.bharath.agri.data.local.tables.MovieCast
import com.bharath.agri.data.local.tables.MovieCrew
import com.bharath.agri.data.local.tables.MovieDetail
import com.bharath.agri.data.local.tables.PopularMovie
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RoomDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllMovies(appointmentList: ArrayList<PopularMovie>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addMovieDetail(movieDetail: MovieDetail): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCast(movieCastList: ArrayList<MovieCast>): Completable

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun addAllCrew(movieCrewList: ArrayList<MovieCrew>): Completable

    @Query("SELECT * FROM popular_movies_table ORDER BY title DESC")
    fun getAllMoviesDb(): Single<List<PopularMovie>>

    @Query("SELECT * FROM movie_detail_table where movie_id  = :movieId")
    fun getMovieDetail(movieId: Int): Single<MovieDetail>

    @Query("SELECT * FROM movie_cast_table where movie_id  = :movieId")
    fun getAllCastsFromMovie(movieId: Int): Single<List<MovieCast>>

    @Query("SELECT * FROM movie_crew_table where movie_id  = :movieId")
    fun getAllCrewsFromMovie(movieId: Int): Single<List<MovieCrew>>
}