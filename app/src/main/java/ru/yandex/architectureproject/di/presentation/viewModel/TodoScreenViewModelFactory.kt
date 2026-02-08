package ru.yandex.architectureproject.di.presentation.viewModel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.yandex.architectureproject.di.ServiceLocator
import ru.yandex.architectureproject.presentation.viewModel.TodoScreenViewModel

class TodoScreenViewModelFactory(private val context: Context) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return TodoScreenViewModel(
            addTaskUseCase = ServiceLocator.provideAddTaskUseCase(context),
            deleteTaskUseCase = ServiceLocator.provideDeleteTaskUseCase(context),
            getAllTasksUseCase = ServiceLocator.provideGetAllTasksUseCase(context),
            updateTaskUseCase = ServiceLocator.provideUpdateTaskUseCase(context),
            ioDispatcher = ServiceLocator.provideIoDispatcher()
        ) as T
    }
}