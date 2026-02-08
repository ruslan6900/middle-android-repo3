package ru.yandex.architectureproject.domain.useCase

import kotlinx.coroutines.delay
import ru.yandex.architectureproject.domain.repository.TaskRepository

class UpdateTaskUseCase(
    private val repository: TaskRepository
) {
    suspend operator fun invoke(taskId: Int, isDone: Boolean) {
        repository.updateStatus(taskId, isDone)
        delay(10_000L)
        if (isDone) {
            repository.delete(taskId)
        }
    }
}