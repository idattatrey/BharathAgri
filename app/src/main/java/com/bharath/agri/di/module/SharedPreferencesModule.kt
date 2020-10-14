package com.bharath.agri.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.bharath.agri.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class SharedPreferencesModule {
    @Provides
    @ApplicationScope
    fun providePreferences(
        application: Application
    ): SharedPreferences {
        return application.getSharedPreferences(
            "HealthifyMePrefs", Context.MODE_PRIVATE
        )
    }

    @Provides
    @ApplicationScope
    fun providePreferencesEditor(
        sharedPreferences: SharedPreferences
    ): SharedPreferences.Editor {
        return sharedPreferences.edit()
    }
}