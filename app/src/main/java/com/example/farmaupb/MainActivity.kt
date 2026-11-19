package com.example.farmaupb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.farmaupb.ui.navigation.FarmaNavHost
import com.example.farmaupb.ui.theme.FarmaUPBTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            FarmaUPBTheme {
                FarmaNavHost()
            }
        }
    }
}
