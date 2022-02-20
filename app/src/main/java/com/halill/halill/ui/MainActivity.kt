package com.halill.halill.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.halill.halill.main.HalIllApp
import com.halill.halill.ui.theme.HalIll_AndroidTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HalIll_AndroidTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HalIllApp()
                }
            }
        }
    }
}