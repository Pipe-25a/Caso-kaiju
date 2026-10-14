package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.datacore.kaijuapp.ui.components.ListaMovimiento
import com.datacore.kaijuapp.model.Movimiento

//Pantalla donde se veran todos los Movimientos
//Si Se Seleciona un Movimiento se mostrara la pantalla de Detalles
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMovimiento(
    movimientos: List<Movimiento>
){
    Scaffold(
        topBar = { TopAppBar(title = { Text("Kaiju App") }) }
    ) {
        innerPadding ->
        ListaMovimiento(
            movimientos = movimientos,
            modifier = Modifier.padding(innerPadding)
        )
    }
}