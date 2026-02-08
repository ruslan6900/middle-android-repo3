package ru.yandex.architectureproject.di

import android.content.Context
import kotlinx.coroutines.Dispatchers
import ru.yandex.architectureproject.data.db.TaskDatabase
import ru.yandex.architectureproject.data.repository.TaskRepositoryImpl
import ru.yandex.architectureproject.domain.repository.TaskRepository
import ru.yandex.architectureproject.domain.useCase.*

object ServiceLocator {

    @Volatile
    private var database: TaskDatabase? = null

    private fun provideDatabase(context: Context): TaskDatabase {
        return database ?: synchronized(this) {
            val instance = TaskDatabase.getInstance(context)
            database = instance
            instance
        }
    }

    private fun provideTaskDao(context: Context) = provideDatabase(context).taskDao()

    fun provideRepository(context: Context): TaskRepository {
        return TaskRepositoryImpl(provideTaskDao(context))
    }

    fun provideGetAllTasksUseCase(context: Context) = GetAllTasksUseCase(provideRepository(context))
    fun provideAddTaskUseCase(context: Context) = AddTaskUseCase(provideRepository(context))
    fun provideDeleteTaskUseCase(context: Context) = DeleteTaskUseCase(provideRepository(context))
    fun provideUpdateTaskUseCase(context: Context) = UpdateTaskUseCase(provideRepository(context))

    fun provideIoDispatcher() = Dispatchers.IO
}