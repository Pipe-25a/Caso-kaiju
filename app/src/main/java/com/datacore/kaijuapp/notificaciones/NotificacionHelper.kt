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
    // Desde Android 8 toda notificación necesita un "canal"
    fun crearCanal(context: Context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val canal = NotificationChannel(
                CANAL_STOCK,
                "Alertas de stock",
                NotificationManager.IMPORTANCE_HIGH
            ).apply { description = "Avisos cuando un producto tiene stock bajo" }

            context.getSystemService(NotificationManager::class.java)
                .createNotificationChannel(canal)
        }
    }
    // En Android 12 o menos siempre es true; en 13+ depende de lo que acepte el usuario
    fun tienePermiso(context: Context): Boolean =
        Build.VERSION.SDK_INT < Build.VERSION_CODES.TIRAMISU ||
                ContextCompat.checkSelfPermission(
                    context, Manifest.permission.POST_NOTIFICATIONS
                ) == PackageManager.PERMISSION_GRANTED
    @SuppressLint("MissingPermission") // ya lo verificamos con tienePermiso()
    fun mostrarStockBajo(context: Context, producto: Producto) {
        if (!tienePermiso(context)) return

        // Al tocar la notificación se abre la app
        val intent = Intent(context, MainActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            context, 0, intent, PendingIntent.FLAG_IMMUTABLE
        )
        val notificacion = NotificationCompat.Builder(context, CANAL_STOCK)
            .setSmallIcon(android.R.drawable.ic_dialog_info) // cámbialo luego por un ícono tuyo
            .setContentTitle("Stock bajo")
            .setContentText("${producto.nombre} está bajo el mínimo (${producto.stockMin})")
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setContentIntent(pendingIntent)
            .setAutoCancel(true)
            .build()
        // El id distinto por producto evita que una notificación pise a otra
        NotificationManagerCompat.from(context).notify(producto.codigo.hashCode(), notificacion)
    }
}