package com.datacore.kaijuapp.viewmodel

import com.datacore.kaijuapp.model.FormularioProductoEstado
import com.datacore.kaijuapp.model.Producto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import com.datacore.kaijuapp.*
private val _estadoInventario = MutableStateFlow(InventarioRepository())

}