package com.example.littlelemon

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable

@Composable
fun MyNavigationComposable(context: Context ,navController: NavHostController) {
    val sharedPreferences = context.getSharedPreferences("order_preferences", Context.MODE_PRIVATE)
    var startDestination = Onboarding.route

    if (sharedPreferences.getBoolean("order_preferences", false)) {
        startDestination = Home.route
    }
    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {
        composable(Onboarding.route) {
            OnBoarding(context, navController)
        }

        composable(Home.route) {
            Home(navController = navController)
        }

        composable(Profile.route) {
            Profile(context ,navController)
        }
    }
}