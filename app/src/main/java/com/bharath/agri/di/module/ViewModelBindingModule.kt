package com.bharath.agri.di.module

import androidx.lifecycle.ViewModel
import com.bharath.agri.di.binds.ViewModelKey
import com.bharath.agri.viewmodel.details_viewmodel.DetailsViewModel
import com.bharath.agri.viewmodel.main_viewmodel.MainViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelBindingModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(DetailsViewModel::class)
    abstract fun bindDetailsViewModel(detailsViewModel: DetailsViewModel): ViewModel
}