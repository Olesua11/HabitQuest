package com.olesya.habitquest

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.lifecycle.viewmodel.compose.viewModel
import com.olesya.habitquest.ui.HabitQuestApp
import com.olesya.habitquest.ui.theme.HabitQuestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            HabitQuestTheme {
                val vm: MainViewModel = viewModel()
                HabitQuestApp(vm)
            }
        }
    }
}
