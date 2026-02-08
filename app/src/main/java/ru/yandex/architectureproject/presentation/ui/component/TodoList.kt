package ru.yandex.architectureproject.presentation.ui.component

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.dp
import ru.yandex.architectureproject.domain.model.Task
import ru.yandex.architectureproject.presentation.action.TaskAction

@Composable
fun TodoList(tasks: List<Task>, onAction: (TaskAction) -> Unit) {
    var text by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .padding(16.dp)
            .systemBarsPadding()
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            TextField(
                value = text,
                onValueChange = { text = it },
                modifier = Modifier.weight(1f)
            )
            Spacer(modifier = Modifier.width(8.dp))
            Button(
                onClick = {
                    if (text.isNotBlank()) {
                        onAction(TaskAction.AddTask(text))
                        text = ""
                    }
                }
            ) {
                Text("Добавить")
            }
        }
        Spacer(modifier = Modifier.height(16.dp))
        LazyColumn {
            items(tasks) { task ->
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(8.dp),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                ) {
                    Text(
                        task.text,
                        style = if (task.isDone) TextStyle(textDecoration = TextDecoration.LineThrough) else TextStyle()
                    )
                    Row {
                        Checkbox(
                            checked = task.isDone,
                            onCheckedChange = {
                                onAction(
                                    TaskAction.UpdateTaskStatus(
                                        task.id,
                                        it
                                    )
                                )
                            }
                        )
                        Spacer(modifier = Modifier.width(8.dp))
                        Button(onClick = { onAction(TaskAction.DeleteTask(task.id)) }) {
                            Text("Удалить")
                        }
                    }
                }
            }
        }
    }
}