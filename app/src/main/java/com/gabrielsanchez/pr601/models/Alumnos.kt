package com.gabrielsanchez.pr601.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "alumnos")
data class Alumnos(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    @ColumnInfo("nombre")
    val nombre: String,
    @ColumnInfo("apellido1")
    val apellido1: String,
    @ColumnInfo("apellido2")
    val apellido2: String,
    @ColumnInfo("nota1")
    val nota1: Float,
    @ColumnInfo("nota2")
    val nota2: Float,
    @ColumnInfo("nota3")
    val nota3: Float
)