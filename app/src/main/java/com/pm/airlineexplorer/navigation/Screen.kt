package com.pm.airlineexplorer.navigation

sealed class Screen(val route: String) {
    object NavParams {
        internal const val PARAM_AIRLINE_ID = "airlineId"
    }
    data object AirlineScreen : Screen("AirlineScreen")

    data object AirlineDetailScreen : Screen("AirlineDetailScreen?airlineId={${NavParams.PARAM_AIRLINE_ID}}") {
        data class Param(val airlineId: String) {
            fun createRoute() = "AirlineDetailScreen?airlineId=$airlineId"
        }
    }
}