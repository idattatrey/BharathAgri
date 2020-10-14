package com.bharath.agri.di.repository

import android.content.SharedPreferences
import com.bharath.agri.data.local.dao.RoomDao
import com.bharath.agri.data.remote.service.ApiService
import com.bharath.agri.di.data_source.LocalDataSource
import com.bharath.agri.di.data_source.RemoteDataSource
import javax.inject.Inject

class AppRepository @Inject constructor(
    private val remoteDataSource: RemoteDataSource,
    private val localDataSource: LocalDataSource
) {
    fun getRemoteDataSource(): ApiService {
        return remoteDataSource.getApiService()
    }

    fun getSharedPreferencesDataSource(): SharedPreferences {
        return localDataSource.getSharedPreferences()
    }

    fun getSharedPreferencesEditorDataSource(): SharedPreferences.Editor {
        return localDataSource.getSharedPreferencesEditor()
    }

    fun getRoomDao(): RoomDao {
        return localDataSource.getRoomDao()
    }

}