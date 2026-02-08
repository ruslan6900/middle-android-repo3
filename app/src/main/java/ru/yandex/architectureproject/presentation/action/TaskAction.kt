package ru.yandex.architectureproject.presentation.action

sealed class TaskAction {
    data object LoadTasks : TaskAction()
    data class  AddTask(val task: String) : TaskAction()
    data class  UpdateTaskStatus(val taskId: Int, val isDone: Boolean) : TaskAction()
    data class  DeleteTask(val taskId: Int) : TaskAction()
}