package com.gabrielsanchez.pr601.views

import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Delete
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.MoreVert
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import java.text.DecimalFormat
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewModelScope
import androidx.navigation.NavController
import com.gabrielsanchez.pr601.navigation.AppScreens
import com.gabrielsanchez.pr601.viewmodels.AlumnosViewModel
import kotlinx.coroutines.launch

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AlumnosHomeView(navController: NavController, viewModel: AlumnosViewModel) {
    var isMenuOpen by rememberSaveable { mutableStateOf(false) }
    val menuItems =
        listOf(
            "Consulta por código",
            "Borrar alumno por código",
            "Consulta por el primer apellido",
            "Consulta por nota"
        )
    val showDialogConsultaPorCodigo = rememberSaveable { mutableStateOf(false) }
    val showDialogDelete = rememberSaveable { mutableStateOf(false) }
    val showDialogConsultaPorApellido = rememberSaveable { mutableStateOf(false) }
    val showDialogConsultaPorNota = rememberSaveable { mutableStateOf(false) }

    if (showDialogConsultaPorCodigo.value) {
        ConsultaPorCodigo(
            onDismissRequest = { showDialogConsultaPorCodigo.value = false },
            onConfirmation = { showDialogConsultaPorCodigo.value = false },
            dialogTitle = "Consulta por código",
            viewModel = viewModel,
            navController = navController
        )
    }

    if (showDialogConsultaPorApellido.value) {
        ConsultaPorApellido(
            onDismissRequest = {
                viewModel.viewModelScope.launch {
                    viewModel.getAllAlumnos()
                }
                showDialogConsultaPorApellido.value = false
            },
            onConfirmation = { showDialogConsultaPorApellido.value = false },
            dialogTitle = "Consulta por apellido",
            viewModel = viewModel,
            navController = navController
        )
    }

    if (showDialogConsultaPorNota.value) {
        ConsultaPorNota(
            onDismissRequest = {
                viewModel.viewModelScope.launch {
                    viewModel.getAllAlumnos()
                }
                showDialogConsultaPorNota.value = false
            },
            onConfirmation = { showDialogConsultaPorNota.value = false },
            dialogTitle = "Consulta por nota",
            viewModel = viewModel,
            navController = navController
        )
    }

    if (showDialogDelete.value) {
        DeletePorCodigo(
            onDismissRequest = { showDialogDelete.value = false },
            onConfirmation = { showDialogDelete.value = false },
            dialogTitle = "Borrar alumno por código",
            viewModel = viewModel
        )
    }

    Scaffold(
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Text(text = "Alumnos", color = Color.White, fontWeight = FontWeight.Bold)
                },
                actions = {
                    IconButton(onClick = { isMenuOpen = true }) {
                        Icon(
                            imageVector = Icons.Default.MoreVert,
                            contentDescription = "Menú",
                            tint = Color.White
                        )
                    }

                    DropdownMenu(
                        expanded = isMenuOpen,
                        onDismissRequest = { isMenuOpen = false }
                    ) {
                        menuItems.forEachIndexed { index, s ->
                            DropdownMenuItem(text = { Text(text = s) }, onClick = {
                                isMenuOpen = false
                                when (index) {
                                    0 -> showDialogConsultaPorCodigo.value = true
                                    1 -> showDialogDelete.value = true
                                    2 -> showDialogConsultaPorApellido.value = true
                                    3 -> showDialogConsultaPorNota.value = true
                                }
                            })
                        }
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primary
                ),
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                onClick = { navController.navigate(AppScreens.NewAlumno.route) },
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = Color.White
            ) {
                Icon(imageVector = Icons.Default.Add, contentDescription = "Agregar")
            }
        }
    ) {
        ContentInicioView(it, navController, viewModel)
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContentInicioView(
    it: PaddingValues,
    navController: NavController,
    viewModel: AlumnosViewModel
) {
    var state = viewModel.state

    Column(
        modifier = Modifier.padding(it)
    ) {
        LazyColumn {
            if (state.alumnosList.isEmpty()) {
                item {
                    Text(
                        text = "No hay alumnos",
                        fontSize = 20.sp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        textAlign = TextAlign.Center
                    )
                }
            } else {
                items(state.alumnosList) {
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 6.dp
                        ),
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(8.dp),
                        onClick = {
                            navController.navigate(AppScreens.EditAlumno.route + "/${it.id}")
                        }
                    ) {
                        Box(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(15.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth(),
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(
                                    text = "#${it.id} - ${it.nombre} ${it.apellido1} ${it.apellido2} \n Nota 1º trimestre: ${it.nota1} \n Nota 2º trimestre: ${it.nota2} \n Nota 3º trimestre: ${it.nota3}",
                                    textAlign = TextAlign.Start,
                                    fontSize = 15.sp,
                                    fontWeight = FontWeight.Bold, // Aumenta el grosor del texto
                                    modifier = Modifier
                                        .weight(1f),
                                )
                                // Mostramos su nota media de las 3 evaluaciones
                                val notaMedia = (it.nota1 + it.nota2 + it.nota3) / 3
                                val formattedNotaMedia = DecimalFormat("#.##").format(notaMedia)

                                Text(
                                    text = "Nota media:\n ${formattedNotaMedia}",
                                    textAlign = TextAlign.End,
                                    fontSize = 20.sp,
                                    fontWeight = FontWeight.Bold, // Aumenta el grosor del texto
                                    modifier = Modifier
                                        .weight(1f),
                                )
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun ConsultaPorCodigo(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    viewModel: AlumnosViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    var codigo by remember { mutableStateOf("") }

    AlertDialog(
        icon = {
            Icon(Icons.Default.Edit, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código del alumno") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done,
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Lanzamos una corrutina para obtener el articulo
                    viewModel.viewModelScope.launch {
                        val alumno = viewModel.getAlumnoById(codigo.toInt())
                        if (alumno != null) {
                            navController.navigate(AppScreens.EditAlumno.route + "/${alumno.id}")
                        } else {
                            Toast.makeText(
                                context,
                                "El alumno no existe",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}

@Composable
fun ConsultaPorApellido(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    viewModel: AlumnosViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    var apellido by remember { mutableStateOf("") }
    AlertDialog(
        icon = {
            Icon(Icons.Default.Edit, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            OutlinedTextField(
                value = apellido,
                onValueChange = { apellido = it },
                label = { Text("Apellido del alumno") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    imeAction = ImeAction.Done,
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Lanzamos una corrutina para obtener el articulo
                    viewModel.viewModelScope.launch {
                        viewModel.getAlumnosByApellido(apellido)
                    }

                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Borrar filtro")
            }
        }
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConsultaPorNota(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    viewModel: AlumnosViewModel,
    navController: NavController,
    context: Context = LocalContext.current
) {
    var expanded by remember { mutableStateOf(false) }
    val opcionNotas = arrayOf("1º Trimestre", "2º Trimestre", "3º Trimestre")
    var selectedOpcionNota by remember { mutableStateOf(opcionNotas[0]) }
    var nota by remember { mutableStateOf("") }
    AlertDialog(
        icon = {
            Icon(Icons.Default.Edit, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                ExposedDropdownMenuBox(
                    expanded = expanded,
                    onExpandedChange = {
                        expanded = !expanded
                    }
                ) {
                    TextField(
                        value = selectedOpcionNota,
                        onValueChange = {},
                        readOnly = true,
                        trailingIcon = {
                            ExposedDropdownMenuDefaults.TrailingIcon(
                                expanded = expanded
                            )
                        },
                        modifier = Modifier.menuAnchor()
                    )

                    ExposedDropdownMenu(
                        expanded = expanded,
                        onDismissRequest = { expanded = false }
                    ) {
                        opcionNotas.forEach { item ->
                            DropdownMenuItem(
                                text = { Text(text = item) },
                                onClick = {
                                    selectedOpcionNota = item
                                    expanded = false
                                }
                            )
                        }
                    }
                }

                Spacer(modifier = Modifier.height(8.dp)) // Agrega un espacio vertical entre los elementos


                OutlinedTextField(
                    value = nota,
                    onValueChange = { nota = it },
                    label = { Text("Nota del alumno") },
                    keyboardOptions = KeyboardOptions.Default.copy(
                        keyboardType = KeyboardType.Number,
                        imeAction = ImeAction.Done,
                    )
                )
            }
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Lanzamos una corrutina para obtener el articulo
                    viewModel.viewModelScope.launch {
                        if (selectedOpcionNota == "1º Trimestre") {
                            viewModel.getAlumnosByNota1(nota.toFloat())
                        } else if (selectedOpcionNota == "2º Trimestre") {
                            viewModel.getAlumnosByNota2(nota.toFloat())
                        } else if (selectedOpcionNota == "3º Trimestre") {
                            viewModel.getAlumnosByNota3(nota.toFloat())
                        }
                    }
                    onConfirmation()
                }
            ) {
                Text("Confirmar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Borrar filtro")
            }
        }
    )
}

@Composable
fun DeletePorCodigo(
    onDismissRequest: () -> Unit,
    onConfirmation: () -> Unit,
    dialogTitle: String,
    viewModel: AlumnosViewModel,
    context: Context = LocalContext.current
) {
    var codigo by remember { mutableStateOf("") }

    AlertDialog(
        icon = {
            Icon(Icons.Default.Delete, contentDescription = "Example Icon")
        },
        title = {
            Text(text = dialogTitle)
        },
        text = {
            OutlinedTextField(
                value = codigo,
                onValueChange = { codigo = it },
                label = { Text("Código del alumno") },
                keyboardOptions = KeyboardOptions.Default.copy(
                    keyboardType = KeyboardType.Number,
                    imeAction = ImeAction.Done
                )
            )
        },
        onDismissRequest = {
            onDismissRequest()
        },
        confirmButton = {
            TextButton(
                onClick = {
                    // Comprobamos que el articulo existe
                    viewModel.viewModelScope.launch {
                        val alumno = viewModel.getAlumnoById(codigo.toInt())
                        if (alumno == null) {
                            Toast.makeText(
                                context,
                                "El alumno no existe",
                                Toast.LENGTH_SHORT
                            ).show()
                        } else {
                            viewModel.deleteArticuloByCodigo(codigo.toInt())
                            Toast.makeText(
                                context,
                                "Alumno borrado correctamente",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                    onConfirmation()
                }
            ) {
                Text("Eliminar")
            }
        },
        dismissButton = {
            TextButton(
                onClick = {
                    onDismissRequest()
                }
            ) {
                Text("Cancelar")
            }
        }
    )
}