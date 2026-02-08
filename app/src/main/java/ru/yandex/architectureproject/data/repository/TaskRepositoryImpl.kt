package ru.yandex.architectureproject.data.repository

import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import ru.yandex.architectureproject.data.dao.TaskDao
import ru.yandex.architectureproject.data.mapper.TaskMapper
import ru.yandex.architectureproject.data.model.TaskEntity
import ru.yandex.architectureproject.domain.model.Task
import ru.yandex.architectureproject.domain.repository.TaskRepository

class TaskRepositoryImpl(
    private val taskDao: TaskDao
) : TaskRepository {

    override fun getAllTasks(): Flow<List<Task>> =
        taskDao.getAllTasks().map { entities ->
            entities.map { TaskMapper.mapToDomain(it) }
        }

    override suspend fun addTask(text: String) =
        taskDao.addTask(TaskEntity(text = text))

    override suspend fun updateStatus(taskId: Int, isDone: Boolean) =
        taskDao.updateTaskStatus(taskId, isDone)

    override suspend fun delete(taskId: Int) =
        taskDao.deleteTask(taskId)
}