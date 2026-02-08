package ru.yandex.architectureproject.domain.model

data class Task(
    val id: Int,
    val text: String,
    val isDone: Boolean
)