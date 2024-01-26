package com.gabrielsanchez.pr601.viewmodels

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.gabrielsanchez.pr601.models.Alumnos
import com.gabrielsanchez.pr601.room.AlumnosDatabaseDao
import com.gabrielsanchez.pr601.states.AlumnosState
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class AlumnosViewModel(
    private val dao: AlumnosDatabaseDao
) : ViewModel() {
    var state by mutableStateOf(AlumnosState())
        private set

    init {
        viewModelScope.launch {
            dao.getAllAlumnos().collectLatest {
                state = state.copy(
                    alumnosList = it
                )
            }
        }
    }

    fun insertAlumno(alumno: Alumnos) = viewModelScope.launch {
        dao.insertAlumno(alumno)
    }

    fun updateAlumno(alumno: Alumnos) = viewModelScope.launch {
        dao.updateAlumno(alumno)
    }

    fun deleteArticuloByCodigo(id: Int) = viewModelScope.launch {
        dao.deleteAlumnoById(id)
    }

    suspend fun getAlumnoById(id: Int): Alumnos? {
        return viewModelScope.async {
            dao.getAlumnoById(id)
        }.await()
    }

    suspend fun getAlumnosByApellido(apellido1: String) {
        val alumnos = viewModelScope.async {
            dao.getAlumnosByApellido(apellido1)
        }.await()
        state = state.copy(alumnosList = alumnos)
    }

    suspend fun getAlumnosByNota1(nota: Float) {
        val alumnos = viewModelScope.async {
            dao.getAlumnosByNota1(nota)
        }.await()
        state = state.copy(alumnosList = alumnos)
    }

    suspend fun getAlumnosByNota2(nota: Float) {
        val alumnos = viewModelScope.async {
            dao.getAlumnosByNota2(nota)
        }.await()
        state = state.copy(alumnosList = alumnos)
    }

    suspend fun getAlumnosByNota3(nota: Float) {
        val alumnos = viewModelScope.async {
            dao.getAlumnosByNota3(nota)
        }.await()
        state = state.copy(alumnosList = alumnos)
    }

    suspend fun getAllAlumnos() {
        dao.getAllAlumnos().collectLatest {
            state = state.copy(
                alumnosList = it
            )
        }
    }
}