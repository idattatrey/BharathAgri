package com.bharath.agri.di.data_source

import com.bharath.agri.data.remote.service.ApiService
import javax.inject.Inject

class RemoteDataSource @Inject constructor(
    private val apiService: ApiService
){
    fun getApiService(): ApiService {
        return apiService
    }
}