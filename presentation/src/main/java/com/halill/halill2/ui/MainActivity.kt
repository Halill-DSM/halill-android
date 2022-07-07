package com.halill.halill2.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import com.halill.halill2.ui.theme.HalIll_AndroidTheme
import com.jakewharton.threetenabp.AndroidThreeTen
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            HalIll_AndroidTheme {
                AndroidThreeTen.init(this)

                Surface(color = MaterialTheme.colors.background) {
                    HalIllApp()
                }
            }
        }
    }
}