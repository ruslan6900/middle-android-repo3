package ru.yandex.architectureproject.domain.useCase

import ru.yandex.architectureproject.domain.repository.TaskRepository

class AddTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(task: String) {
        repository.addTask(task)
    }
}