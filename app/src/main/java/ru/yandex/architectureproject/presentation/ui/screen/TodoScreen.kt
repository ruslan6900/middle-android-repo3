package ru.yandex.architectureproject.presentation.ui.screen

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import ru.yandex.architectureproject.di.presentation.viewModel.TodoScreenViewModelFactory
import ru.yandex.architectureproject.presentation.action.StatusAction
import ru.yandex.architectureproject.presentation.ui.component.TodoList
import ru.yandex.architectureproject.presentation.viewModel.TodoScreenViewModel

@Composable
fun TodoScreen() {
    val viewModel: TodoScreenViewModel = viewModel(
        factory = TodoScreenViewModelFactory(LocalContext.current.applicationContext)
    )

    val action by viewModel.statusAction.collectAsState()

    Box(
        modifier = Modifier
            .fillMaxSize()
            .systemBarsPadding(),
        contentAlignment = Alignment.Center,
    ) {
        when (action) {
            is StatusAction.Error -> Text(text = (action as Error).message ?: "Ошибка")
            is StatusAction.Loaded -> {
                val tasks = (action as StatusAction.Loaded).tasks
                TodoList(tasks = tasks, onAction = { action -> viewModel.reduce(action) })
            }
            StatusAction.Loading -> CircularProgressIndicator()
        }
    }
}