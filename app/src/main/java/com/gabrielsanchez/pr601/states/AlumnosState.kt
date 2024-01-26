package com.gabrielsanchez.pr601.states

import com.gabrielsanchez.pr601.models.Alumnos

data class AlumnosState(
    val alumnosList: List<Alumnos> = emptyList()
)