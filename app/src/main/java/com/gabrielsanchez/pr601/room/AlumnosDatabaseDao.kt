package com.gabrielsanchez.pr601.room

import androidx.room.Dao

import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

import androidx.room.Update
import com.gabrielsanchez.pr601.models.Alumnos
import kotlinx.coroutines.flow.Flow

@Dao
interface AlumnosDatabaseDao {

    @Query("SELECT * FROM alumnos")
    fun getAllAlumnos(): Flow<List<Alumnos>>

    @Query("SELECT * FROM alumnos WHERE id = :id")
    fun getAlumnobyId(id: Int): Flow<Alumnos>

    @Insert
    suspend fun insertAlumno(alumno: Alumnos)

    @Update
    suspend fun updateAlumno(alumno: Alumnos)

    @Query("SELECT * FROM alumnos WHERE id = :id")
    suspend fun getAlumnoById(id: Int): Alumnos

    @Query("SELECT * FROM alumnos WHERE apellido1 LIKE :apellido1")
    suspend fun getAlumnosByApellido(apellido1: String): List<Alumnos>

    @Query("SELECT * FROM alumnos WHERE nota1 LIKE :nota")
    suspend fun getAlumnosByNota1(nota: Float): List<Alumnos>

    @Query("SELECT * FROM alumnos WHERE nota2 LIKE :nota")
    suspend fun getAlumnosByNota2(nota: Float): List<Alumnos>

    @Query("SELECT * FROM alumnos WHERE nota3 LIKE :nota")
    suspend fun getAlumnosByNota3(nota: Float): List<Alumnos>

    @Query("DELETE FROM alumnos WHERE id = :id")
    suspend fun deleteAlumnoById(id: Int)

    @Delete
    suspend fun deleteAlumno(alumno: Alumnos)
}