package com.gabrielsanchez.pr601.room

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import com.gabrielsanchez.pr601.models.Alumnos

@Database(
    version = 1,
    entities = [Alumnos::class],
    exportSchema = false,
)
abstract class AlumnosDatabase: RoomDatabase() {

    abstract fun alumnosDao(): AlumnosDatabaseDao
}