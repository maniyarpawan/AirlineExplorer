package com.pm.airlineexplorer.airlineexplorerlist.dao.di

import com.pm.airlineexplorer.airlineexplorerlist.dao.AirlineDao
import com.pm.airlineexplorer.airlineexplorerlist.dao.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DaoModule {

    @Provides
    @Singleton
    fun provideAirlineDao(db: AppDatabase): AirlineDao = db.airlineDao()
}