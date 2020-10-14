package com.bharath.agri.data.local.tables

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "popular_movies_table")
data class PopularMovie(
    @PrimaryKey
    var id: Int,
    var popularity: Double,
    var vote_count: Int,
    var poster_path: String,
    var backdrop_path: String,
    var original_language: String,
    var original_title: String,
    var title: String,
    var vote_average: Double,
    var overview: String,
    var release_date: String
)