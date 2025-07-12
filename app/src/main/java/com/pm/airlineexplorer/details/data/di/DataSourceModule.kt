package com.pm.airlineexplorer.details.data.di

import com.pm.airlineexplorer.details.data.sources.AirlineDetailsDataSource
import com.pm.airlineexplorer.details.data.sources.AirlineDetailsDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    abstract fun bindAirlineExplorerListSource(
        impl: AirlineDetailsDataSourceImpl
    ): AirlineDetailsDataSource

}