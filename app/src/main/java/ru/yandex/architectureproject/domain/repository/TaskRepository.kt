package ru.yandex.architectureproject.domain.repository

import kotlinx.coroutines.flow.Flow
import ru.yandex.architectureproject.domain.model.Task

interface TaskRepository {
    fun getAllTasks(): Flow<List<Task>>
    suspend fun addTask(text: String)
    suspend fun updateStatus(taskId: Int, isDone: Boolean)
    suspend fun delete(taskId: Int)
}