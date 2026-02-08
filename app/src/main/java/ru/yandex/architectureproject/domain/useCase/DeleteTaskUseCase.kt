package ru.yandex.architectureproject.domain.useCase

import ru.yandex.architectureproject.domain.repository.TaskRepository

class DeleteTaskUseCase(
    private val repository: TaskRepository,
) {
    suspend operator fun invoke(taskId: Int) {
        repository.delete(taskId)
    }
}