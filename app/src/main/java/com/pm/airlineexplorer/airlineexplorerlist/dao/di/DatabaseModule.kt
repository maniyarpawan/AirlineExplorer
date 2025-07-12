package com.pm.airlineexplorer.airlineexplorerlist.dao.di

import android.content.Context
import androidx.room.Room
import com.pm.airlineexplorer.airlineexplorerlist.dao.AppDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val PREF_NAME = "airline_secure_pref_file"

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(
            context.applicationContext,
            AppDatabase::class.java,
            "airline_db"
        ).build()
    }
}