package com.bharath.agri.di.data_source


import android.content.SharedPreferences
import com.bharath.agri.data.local.dao.RoomDao
import javax.inject.Inject

class LocalDataSource @Inject constructor(
    private val sharedPreferences: SharedPreferences,
    private val sharedPreferencesEditor: SharedPreferences.Editor,
    private val roomDao: RoomDao
) {
    fun getSharedPreferences(): SharedPreferences {
        return sharedPreferences
    }

    fun getSharedPreferencesEditor(): SharedPreferences.Editor {
        return sharedPreferencesEditor
    }

    fun getRoomDao(): RoomDao {
        return roomDao
    }
}