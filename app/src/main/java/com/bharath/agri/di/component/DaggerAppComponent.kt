package com.bharath.agri.di.component

import android.app.Application
import android.content.SharedPreferences
import com.bharath.agri.di.module.SharedPreferencesModule
import com.bharath.agri.di.module.ViewModelBindingModule
import com.bharath.agri.ui.activities.MainActivity
import com.bharath.agri.ui.activities.SplashActivity
import com.bharath.agri.di.module.NetworkModule
import com.bharath.agri.di.module.RoomDatabaseModule
import com.bharath.agri.di.scope.ApplicationScope
import com.bharath.agri.ui.activities.DetailsActivity
import dagger.BindsInstance
import dagger.Component


@ApplicationScope
@Component(modules = [NetworkModule::class, ViewModelBindingModule::class, SharedPreferencesModule::class, RoomDatabaseModule::class])
interface AppComponent {

    fun inject(mainActivity: MainActivity)

    fun inject(detailsActivity: DetailsActivity)

    fun getSharedPrefs(): SharedPreferences?

    fun getSharedPrefsEditor(): SharedPreferences.Editor?

    @Component.Builder
    interface Builder {
        fun build(): AppComponent?

        @BindsInstance
        fun application(application: Application): Builder?
    }
}