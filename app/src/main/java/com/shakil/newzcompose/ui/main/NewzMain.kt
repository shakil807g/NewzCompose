package com.shakil.newzcompose.ui.main

import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.AmbientContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.shakil.newzcompose.ui.headline.HeadlineScreen
import com.shakil.newzcompose.ui.home.HomeScreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun NewzMain(headlinesViewModel: MainViewModel) {
    val navController = rememberNavController()
    NavHost(navController = navController, startDestination = NewzScreen.Home.route) {
        composable(NewzScreen.Home.route) {
            //HomeScreen(headlinesViewModel)
            HeadlineScreen(headlinesViewModel)
        }
    }
}

sealed class NewzScreen(val route: String) {
    object Home : NewzScreen("Home")
}