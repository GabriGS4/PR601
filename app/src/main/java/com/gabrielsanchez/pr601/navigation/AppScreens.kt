package com.gabrielsanchez.pr601.navigation

sealed class AppScreens(val route: String) {
    object Alumnos : AppScreens("home")
    object NewAlumno : AppScreens("newAlumno")
    object EditAlumno : AppScreens("editAlumno")
}