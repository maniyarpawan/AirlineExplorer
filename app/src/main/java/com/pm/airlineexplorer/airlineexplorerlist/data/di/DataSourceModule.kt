package com.pm.airlineexplorer.airlineexplorerlist.data.di

import com.pm.airlineexplorer.airlineexplorerlist.data.api.ApiAirlineService
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.AirlineDataSource
import com.pm.airlineexplorer.airlineexplorerlist.data.sources.AirlineDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAirlineExplorerListSource(
        impl: AirlineDataSourceImpl
    ): AirlineDataSource

    companion object {
        @Provides
        fun provideApiService(): ApiAirlineService {
            return Retrofit.Builder()
                .baseUrl("https://raw.githubusercontent.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiAirlineService::class.java)
        }
    }
}