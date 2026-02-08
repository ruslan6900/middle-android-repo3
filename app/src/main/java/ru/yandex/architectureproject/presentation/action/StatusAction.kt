package ru.yandex.architectureproject.presentation.action

import ru.yandex.architectureproject.domain.model.Task

sealed interface StatusAction {
    data object Loading : StatusAction
    data class Loaded(val tasks: List<Task>) : StatusAction
    data class Error(val message: String) : StatusAction
}