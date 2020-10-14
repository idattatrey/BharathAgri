package com.bharath.agri.di.module

import android.app.Application
import androidx.room.Room
import com.bharath.agri.data.local.dao.RoomDao
import com.bharath.agri.data.local.room.AppDB
import com.bharath.agri.di.scope.ApplicationScope
import dagger.Module
import dagger.Provides

@Module
class RoomDatabaseModule {
    @Provides
    @ApplicationScope
    fun providesRoomDb(application: Application): AppDB {
        return Room.databaseBuilder(application.applicationContext, AppDB::class.java, "BharathAgri")
            .build()
    }

    @Provides
    @ApplicationScope
    fun providesRoomDao(appDB: AppDB): RoomDao {
        return appDB.roomDao()
    }
}