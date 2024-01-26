package com.gabrielsanchez.pr601

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.room.Room
import com.gabrielsanchez.pr601.navigation.NavManager
import com.gabrielsanchez.pr601.room.AlumnosDatabase
import com.gabrielsanchez.pr601.viewmodels.AlumnosViewModel

import androidx.compose.ui.tooling.preview.Preview
import com.gabrielsanchez.pr601.ui.theme.PR601Theme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            PR601Theme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val database = Room.databaseBuilder(this, AlumnosDatabase::class.java, "db_alumnado").build()
                    val dao = database.alumnosDao()

                    val viewModel = AlumnosViewModel(dao)
                    NavManager(viewModel = viewModel)
                }
            }
        }
    }
}