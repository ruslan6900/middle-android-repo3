package ru.yandex.architectureproject.presentation.ui

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import ru.yandex.architectureproject.presentation.ui.screen.TodoScreen
import ru.yandex.architectureproject.presentation.ui.theme.ArchitectureProjectTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            ArchitectureProjectTheme {
                TodoScreen()
            }
        }
    }
}