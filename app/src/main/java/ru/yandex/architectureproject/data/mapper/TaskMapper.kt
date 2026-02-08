package ru.yandex.architectureproject.data.mapper

import ru.yandex.architectureproject.data.model.TaskEntity
import ru.yandex.architectureproject.domain.model.Task

object TaskMapper {
    fun mapToDomain(entity: TaskEntity): Task {
        return Task(id = entity.id, text = entity.text, isDone = entity.isDone)
    }
}