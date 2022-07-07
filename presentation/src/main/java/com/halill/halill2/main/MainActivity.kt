package com.halill.halill2.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.halill.halill2.ui.HalIllApp
import com.halill.halill2.theme.HalIll_AndroidTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        AndroidThreeTen.init(this)
        setContent {
            HalIll_AndroidTheme {
                Surface(color = MaterialTheme.colors.background) {
                    HalIllApp()
                }
            }
        }
    }
}