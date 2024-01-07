package com.aviral.stockpulse

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.aviral.stockpulse.ui.theme.StockPulseTheme

class MainActivity : ComponentActivity() {
//    9UXMUFC3RTMJP372
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            StockPulseTheme {

            }
        }
    }
}