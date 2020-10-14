package com.bharath.agri.data.local.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bharath.agri.data.local.dao.RoomDao
import com.bharath.agri.data.local.tables.MovieCast
import com.bharath.agri.data.local.tables.MovieCrew
import com.bharath.agri.data.local.tables.MovieDetail
import com.bharath.agri.data.local.tables.PopularMovie

@Database(
    entities = [PopularMovie::class, MovieDetail::class, MovieCast::class, MovieCrew::class],
    version = 1,
    exportSchema = false
)
abstract class AppDB : RoomDatabase() {
    abstract fun roomDao(): RoomDao
}