package com.datacore.kaijuapp.ui.screen

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.datacore.kaijuapp.data.remote.PokeApiClient
import com.datacore.kaijuapp.repository.PokemonRepository
import com.datacore.kaijuapp.ui.components.HomeHeader
import com.datacore.kaijuapp.ui.components.KaijuEasterEggDialog
import com.datacore.kaijuapp.ui.theme.*
import com.datacore.kaijuapp.viewmodel.KaijuEasterEggViewModel
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaMenu(
    nombreUsuario: String,
    rol: String,
    onCatalogo: () -> Unit,
    onMovimientos: () -> Unit,
    onUsuarios: (() -> Unit)? = null,
    onCerrarSesion: () -> Unit,
    // NUEVO: ViewModel del Easter egg (no cambia cómo llamas a PantallaMenu)
    easterEggVm: KaijuEasterEggViewModel = viewModel(
        factory = viewModelFactory {
            initializer { KaijuEasterEggViewModel(PokemonRepository(PokeApiClient.api)) }
        }
    )
) {
    val easterEgg by easterEggVm.estado.collectAsState()
    Scaffold(
        containerColor = MaterialTheme.colorScheme.background,
        topBar = {
            TopAppBar(
                title = { Text("Menú principal", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = KaijuDarkPurple,
                    titleContentColor = KaijuYellow,
                    actionIconContentColor = KaijuYellow
                ),
                actions = {
                    TextButton(onClick = onCerrarSesion) {
                        Text("Cerrar sesión", color = KaijuYellow)
                    } }) }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .padding(horizontal = 16.dp)
                .fillMaxSize(),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Box(
                modifier = Modifier.clickable(
                    interactionSource = remember { MutableInteractionSource() },
                    indication = null
                ) { easterEggVm.onEasterEggClick() }
            ) {
                HomeHeader()
            }
            Spacer(Modifier.height(8.dp))
            Text(
                text = "Hola, $nombreUsuario",
                style = MaterialTheme.typography.headlineMedium,
                color = KaijuOnDark
            )
            Text(
                text = "Rol: $rol",
                style = MaterialTheme.typography.bodyMedium,
                color = KaijuYellow
            )
            Spacer(Modifier.height(16.dp))
            Button(
                onClick = onCatalogo,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KaijuYellow,
                    contentColor = MaterialTheme.colorScheme.onTertiary
                )
            ) { Text("Catálogo", fontWeight = FontWeight.Bold) }
            Button(
                onClick = onMovimientos,
                modifier = Modifier.fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = KaijuPurple,
                    contentColor = KaijuOnDark
                )
            ) { Text("Movimientos") }
            OutlinedButton(
                onClick = { onUsuarios?.invoke() },
                enabled = onUsuarios != null,
                modifier = Modifier.fillMaxWidth(),
                border = BorderStroke(1.dp, KaijuPurpleLight),
                colors = ButtonDefaults.outlinedButtonColors(contentColor = KaijuPurpleLight)
            ) {
                Text(if (onUsuarios != null) "Usuarios" else "Usuarios (próximamente)")
            }
        }
    }
    KaijuEasterEggDialog(
        estado = easterEgg,
        onReintentar = easterEggVm::invocarKaiju,
        onCerrar = easterEggVm::cerrar
    )
}