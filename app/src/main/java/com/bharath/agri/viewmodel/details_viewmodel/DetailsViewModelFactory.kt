package com.bharath.agri.viewmodel.details_viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.bharath.agri.di.repository.AppRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class DetailsViewModelFactory @Inject constructor(
    private val appRepository: AppRepository
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return if (modelClass.isAssignableFrom(DetailsViewModel::class.java)) {
            DetailsViewModel(
                appRepository
            ) as T
        } else {
            throw IllegalArgumentException("ViewModel Not Found")
        }
    }
}