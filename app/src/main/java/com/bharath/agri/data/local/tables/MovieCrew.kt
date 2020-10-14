package com.bharath.agri.data.local.tables


import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "movie_crew_table")
data class MovieCrew(
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0,
    var movie_id: Int,
    var cast_name: String?,
    var profile_path: String?,
    var job: String?
)