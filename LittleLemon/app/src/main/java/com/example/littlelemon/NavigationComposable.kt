package com.ivy.dev.orderapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.example.littlelemon.Home
import com.example.littlelemon.OnBoarding
import com.example.littlelemon.Onboarding
import com.example.littlelemon.Profile

@Composable
fun MyNavigationComposable(navController: NavHostController) {

    NavHost(
        navController = navController,
        startDestination = Onboarding.route
    ) {
        composable(Home.route) {
            Home(navController = navController)
        }

        composable(Onboarding.route) {
            OnBoarding(navController = navController)
        }

        composable(Profile.route) {
            Profile(context = LocalContext.current, navController)
        }

    }

}