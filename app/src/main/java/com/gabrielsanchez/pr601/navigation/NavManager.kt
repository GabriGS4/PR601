package com.gabrielsanchez.pr601.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.gabrielsanchez.pr601.viewmodels.AlumnosViewModel
import com.gabrielsanchez.pr601.views.AlumnosHomeView
import com.gabrielsanchez.pr601.views.EditAlumnoView
import com.gabrielsanchez.pr601.views.NewAlumnoView

@Composable
fun NavManager(viewModel: AlumnosViewModel) {
    val navController = rememberNavController()

    NavHost(navController = navController, startDestination = AppScreens.Alumnos.route) {
        composable(AppScreens.Alumnos.route) {
            AlumnosHomeView(navController, viewModel)
        }

        composable(AppScreens.NewAlumno.route) {
            NewAlumnoView(navController, viewModel)
        }

        composable(AppScreens.EditAlumno.route + "/{id}", arguments = listOf(
            navArgument("id") {
                type = NavType.IntType
            }
        )) {
            EditAlumnoView(
                navController,
                viewModel,
                it.arguments!!.getInt("id"),
            )
        }
    }
}