package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import com.datacore.kaijuapp.model.Movimiento
import com.datacore.kaijuapp.ui.components.ListaMovimiento
import com.datacore.kaijuapp.ui.theme.KaijuDarkPurple
import com.datacore.kaijuapp.ui.theme.KaijuYellow

// Pantalla donde se ven todos los movimientos.
// Al tocar uno se abre la pantalla de detalles.
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMovimiento(
    movimientos: List<Movimiento>,
    onSeleccionar: (Movimiento) -> Unit,
    onVolver: () -> Unit
) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Movimientos", fontWeight = FontWeight.Bold) },
                navigationIcon = {
                    IconButton(onClick = onVolver) {
                        Icon(
                            Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Volver",
                            tint = KaijuYellow
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KaijuDarkPurple,
                    titleContentColor = KaijuYellow
                )
            )
        }
    ) { innerPadding ->
        ListaMovimiento(
            movimientos = movimientos,
            modifier = Modifier.padding(innerPadding),
            onSeleccionar = onSeleccionar
        )
    }
}