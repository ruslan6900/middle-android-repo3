package ru.yandex.architectureproject.presentation.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.job
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ru.yandex.architectureproject.domain.useCase.AddTaskUseCase
import ru.yandex.architectureproject.domain.useCase.DeleteTaskUseCase
import ru.yandex.architectureproject.domain.useCase.GetAllTasksUseCase
import ru.yandex.architectureproject.domain.useCase.UpdateTaskUseCase
import ru.yandex.architectureproject.presentation.action.TaskAction
import ru.yandex.architectureproject.presentation.action.StatusAction

class TodoScreenViewModel(
    private val addTaskUseCase: AddTaskUseCase,
    private val deleteTaskUseCase: DeleteTaskUseCase,
    private val getAllTasksUseCase: GetAllTasksUseCase,
    private val updateTaskUseCase: UpdateTaskUseCase,
    private val ioDispatcher: CoroutineDispatcher,
) : ViewModel() {
    private val _Status_action = MutableStateFlow<StatusAction>(StatusAction.Loading)
    val statusAction: StateFlow<StatusAction> = _Status_action.asStateFlow()

    private val taskForDeletionJobMap: MutableMap<Int, Job?> = mutableMapOf()

    init {
        reduce(TaskAction.LoadTasks)
    }

    fun reduce(action: TaskAction) {
        viewModelScope.launch {
            when (action) {
                is TaskAction.LoadTasks -> loadTasks()
                is TaskAction.AddTask -> addTaskUseCase(action.task)
                is TaskAction.UpdateTaskStatus -> {
                    if (action.isDone) {
                        taskForDeletionJobMap[action.taskId] = this.coroutineContext.job
                        updateTaskUseCase(action.taskId, true)
                    } else {
                        taskForDeletionJobMap[action.taskId]?.cancel()
                        updateTaskUseCase(action.taskId, false)
                    }
                }
                is TaskAction.DeleteTask -> deleteTaskUseCase(action.taskId)
            }
        }
    }

    private suspend fun loadTasks() {
        withContext(ioDispatcher) {
            getAllTasksUseCase()
                .distinctUntilChanged()
                .onStart { _Status_action.value = StatusAction.Loading }
                .catch { e -> _Status_action.value = StatusAction.Error(e.message ?: "Ошибка загрузки") }
                .collect { tasks -> _Status_action.value = StatusAction.Loaded(tasks) }
        }
    }
}