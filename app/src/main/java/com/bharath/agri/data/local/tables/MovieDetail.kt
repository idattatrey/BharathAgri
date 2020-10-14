package com.bharath.agri.data.local.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_detail_table")
data class MovieDetail(
    @PrimaryKey
    var movie_id: Int,
    var backdrop_path: String,
    var genre: String,
    var imdb_id: String,
    var original_title: String,
    var title: String,
    var overview: String,
    var poster_path: String,
    var language: String,
    var vote_average: Double,
    var time: Int,
    var release_date: String,
    var status: String
)