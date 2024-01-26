package com.gabrielsanchez.pr601.views

import android.annotation.SuppressLint
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
import kotlinx.coroutines.launch

@SuppressLint("CoroutineCreationDuringComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAlumnoView(navController: NavController, viewModel: AlumnosViewModel, id: Int) {
    var nombre = remember { mutableStateOf<String?>(null) }
    var apellido1 = remember { mutableStateOf<String?>(null) }
    var apellido2 = remember { mutableStateOf<String?>(null) }
    var nota1 = remember { mutableStateOf<Float?>(null) }
    var nota2 = remember { mutableStateOf<Float?>(null) }
    var nota3 = remember { mutableStateOf<Float?>(null) }
    viewModel.viewModelScope.launch {
        val alumno = viewModel.getAlumnoById(id)
        if (alumno != null) {
            nombre.value = alumno.nombre
            apellido1.value = alumno.apellido1
            apellido2.value = alumno.apellido2
            nota1.value = alumno.nota1
            nota2.value = alumno.nota2
            nota3.value = alumno.nota3
        }
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Editar Alumno #${id}", color = Color.White, fontWeight = FontWeight.Bold)
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
                    if (nombre.value.isNullOrEmpty() || apellido1.value.isNullOrEmpty() || apellido2.value.isNullOrEmpty() || nota1.value == null || nota2.value == null || nota3.value == null) {
                        Toast.makeText(navController.context, "Todos los campos son requeridos", Toast.LENGTH_SHORT).show()
                        return@FloatingActionButton
                    }
                    if (nota1.value!! < 0 || nota1.value!! > 10 || nota2.value!! < 0 || nota2.value!! > 10 || nota3.value!! < 0 || nota3.value!! > 10) {
                        Toast.makeText(navController.context, "Las notas deben estar entre 0 y 10", Toast.LENGTH_SHORT).show()
                        return@FloatingActionButton
                    }
                    val alumno = Alumnos(
                        id = id,
                        nombre = nombre.value!!,
                        apellido1 = apellido1.value!!,
                        apellido2 = apellido2.value!!,
                        nota1 = nota1.value!!,
                        nota2 = nota2.value!!,
                        nota3 = nota3.value!!,
                    )

                    viewModel.updateAlumno(alumno)
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
        ContentEditAlumnoView(it, nombre, apellido1, apellido2, nota1, nota2, nota3)
    }
}

@Composable
fun ContentEditAlumnoView(
    it: PaddingValues,
    nombre: MutableState<String?>,
    apellido1: MutableState<String?>,
    apellido2: MutableState<String?>,
    nota1: MutableState<Float?>,
    nota2: MutableState<Float?>,
    nota3: MutableState<Float?>
) {

    Column(
        modifier = Modifier
            .padding(it)
            .padding(top = 30.dp)
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        OutlinedTextField(
            value = nombre.value ?: "",
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
            value = apellido1.value ?: "",
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
            value = apellido2.value ?: "",
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
            value = nota1.value?.toString() ?: "",
            onValueChange = { if (it.isNotEmpty()) nota1.value = it.toFloat() },
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
            value = nota2.value?.toString() ?: "",
            onValueChange = { if (it.isNotEmpty()) nota2.value = it.toFloat() },
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
            value = nota3.value?.toString() ?: "",
            onValueChange = { if (it.isNotEmpty()) nota3.value = it.toFloat() },
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
