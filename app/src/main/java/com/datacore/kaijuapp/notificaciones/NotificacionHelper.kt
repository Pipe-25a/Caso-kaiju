package com.datacore.kaijuapp.notificaciones

import android.Manifest
import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import androidx.core.content.ContextCompat
import com.datacore.kaijuapp.MainActivity
import com.datacore.kaijuapp.model.Producto

object NotificacionHelper {
    private const val CANAL_STOCK = "canal_stock"
    private const val CANAL_ACCIONES = "canal_acciones"
    // ID FIJO: la alerta de stock siempre reemplaza a la anterior (no se acumulan)
    private const val ID_ALERTA_STOCK = 1
    // Las notificaciones de acciones usan ids distintos para que no se pisen
    private var contadorAcciones = 100
    // Desde Android 8 toda notificación necesita un "canal"
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val manager = context.getSystemService(NotificationManager::class.java)

            manager.createNotificationChannel(
                NotificationChannel(CANAL_STOCK, "Alertas de stock", NotificationManager.IMPORTANCE_HIGH)
                    .apply { description = "Avisos del producto con menor stock" }
            )
            manager.createNotificationChannel(
                NotificationChannel(CANAL_ACCIONES, "Acciones del catálogo", NotificationManager.IMPORTANCE_DEFAULT)
                    .apply { description = "Avisos al agregar, modificar o eliminar productos" }
            )
        }
    }
    // En Android 12 o menos siempre es true; en 13+ depende de lo que acepte el usuario
    fun tienePermiso(context: Context): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
    @SuppressLint("MissingPermission") // ya lo verificamos con tienePermiso()
    private fun mostrar(context: Context, canal: String, id: Int, titulo: String, texto: String) {
        if (!tienePermiso(context)) return
        // Al tocar la notificación se abre la app
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notificacion = NotificationCompat.Builder(context, canal)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // cámbialo luego por un ícono tuyo
            .setContentTitle(titulo)
            .setContentText(texto)
            .setStyle(NotificationCompat.BigTextStyle().bigText(texto))
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        NotificationManagerCompat.from(context).notify(id, notificacion)
    }
    // ---------- 1) Alerta del producto con menor stock REAL ----------
    // Llamar después de agregar, modificar o eliminar (y desde el botón de prueba).
    fun actualizarAlertaStock(context: Context, productos: List<Producto>) {
        val critico = productos.minByOrNull { it.stockActual }
        if (critico == null) {
            // catálogo vacío: no hay nada que avisar
            NotificationManagerCompat.from(context).cancel(ID_ALERTA_STOCK)
            return
        }
        val titulo = if (critico.stockActual < critico.stockMin) "Stock bajo" else "Producto con menor stock"
        mostrar(
            context, CANAL_STOCK, ID_ALERTA_STOCK, titulo,
            "${critico.nombre} (ID ${critico.codigo}): quedan ${critico.stockActual} unidades " +
                    "(mínimo ${critico.stockMin})"
        )
    }
    // ---------- 2) Aviso de acciones ----------
    fun notificarAccion(context: Context, titulo: String, texto: String) {
        mostrar(context, CANAL_ACCIONES, contadorAcciones++, titulo, texto)
    }
    // Muestra la alerta de UN producto con su stock real.
    // Usa el ID fijo, así reemplaza a la alerta anterior en vez de acumularse.
    fun mostrarStockBajo(context: Context, producto: Producto) {
        val titulo = if (producto.stockActual < producto.stockMin) "Stock bajo" else "Producto con menor stock"
        mostrar(
            context, CANAL_STOCK, ID_ALERTA_STOCK, titulo,
            "${producto.nombre} (ID ${producto.codigo}): quedan ${producto.stockActual} unidades " +
                    "(mínimo ${producto.stockMin})"
        )
    }
}