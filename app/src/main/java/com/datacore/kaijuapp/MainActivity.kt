package com.datacore.kaijuapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.datacore.kaijuapp.navigation.AppNavHost
import com.datacore.kaijuapp.ui.theme.KaijuAppTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            KaijuAppTheme {
                AppNavHost()
            }
        }
    }
}