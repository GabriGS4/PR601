package com.gabrielsanchez.pr601.views

import android.widget.Toast
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Check
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gabrielsanchez.pr601.models.Alumnos
import com.gabrielsanchez.pr601.viewmodels.AlumnosViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun NewAlumnoView(navController: NavController, viewModel: AlumnosViewModel) {

    var nombre = remember { mutableStateOf("") }
    var apellido1 = remember { mutableStateOf("") }
    var apellido2 = remember { mutableStateOf("") }
    var nota1 = remember { mutableStateOf("") }
    var nota2 = remember { mutableStateOf("") }
    var nota3 = remember { mutableStateOf("") }
    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Nuevo Alumno", color = Color.White, fontWeight = FontWeight.Bold)
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
                navigationIcon = {
                    IconButton(
                        onClick = { navController.popBackStack() }
                    ) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Regresar",
                            tint = Color.White
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = {
                    if (nombre.value.isEmpty() || apellido1.value.isEmpty() || apellido2.value.isEmpty() || nota1.value.isEmpty() || nota2.value.isEmpty() || nota3.value.isEmpty()) {
                        Toast.makeText(
                            navController.context,
                            "Todos los campos son requeridos",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@FloatingActionButton
                    }
                    if (nota1.value.toFloat() > 10 || nota2.value.toFloat() > 10 || nota3.value.toFloat() > 10 || nota1.value.toFloat() < 0 || nota2.value.toFloat() < 0 || nota3.value.toFloat() < 0) {
                        Toast.makeText(
                            navController.context,
                            "Las notas deben estar entre 0 y 10",
                            Toast.LENGTH_SHORT
                        ).show()
                        return@FloatingActionButton
                    }
                    val alumno = Alumnos(
                        nombre = nombre.value,
                        apellido1 = apellido1.value,
                        apellido2 = apellido2.value,
                        nota1 = nota1.value.toFloat(),
                        nota2 = nota2.value.toFloat(),
                        nota3 = nota3.value.toFloat(),
                    )

                    viewModel.insertAlumno(alumno)
                    navController.popBackStack()
                },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White,
                shape = CircleShape,
            ) {
                Icon(imageVector = Icons.Default.Check, contentDescription = "Agregar")
            }
        }
    ) {
        ContentNewAlumnoView(it, nombre, apellido1, apellido2, nota1, nota2, nota3)
    }
}

@Composable
fun ContentNewAlumnoView(
    it: PaddingValues,
    nombre: MutableState<String>,
    apellido1: MutableState<String>,
    apellido2: MutableState<String>,
    nota1: MutableState<String>,
    nota2: MutableState<String>,
    nota3: MutableState<String>
) {

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = nombre.value,
            onValueChange = { nombre.value = it },
            label = { Text(text = "Nombre") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )

        OutlinedTextField(
            value = apellido1.value,
            onValueChange = { apellido1.value = it },
            label = { Text(text = "Primer Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        OutlinedTextField(
            value = apellido2.value,
            onValueChange = { apellido2.value = it },
            label = { Text(text = "Segundo Apellido") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next
            ),
        )
        OutlinedTextField(
            value = nota1.value,
            onValueChange = { nota1.value = it },
            label = { Text(text = "Nota 1º") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
        )
        OutlinedTextField(
            value = nota2.value,
            onValueChange = { nota2.value = it },
            label = { Text(text = "Nota 2º") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number
            ),
        )
        OutlinedTextField(
            value = nota3.value,
            onValueChange = { nota3.value = it },
            label = { Text(text = "Nota 3º") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 30.dp)
                .padding(bottom = 15.dp),
            keyboardOptions = KeyboardOptions.Default.copy(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number
            ),
        )
    }
}