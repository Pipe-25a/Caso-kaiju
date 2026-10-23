package com.datacore.kaijuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.datacore.kaijuapp.navigation.AppNavHost
import com.datacore.kaijuapp.ui.theme.KaijuAppTheme
//esto es para las notificaciones
import com.datacore.kaijuapp.naturalresources.notificaciones.NotificacionHelper
import com.datacore.kaijuapp.ui.screen.MainScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            NotificacionHelper.crearCanal(this)

            setContent {
                KaijuAppTheme {
                    //AppNavHost()
                    MainScreen()
                }
            }
        }
    }
}