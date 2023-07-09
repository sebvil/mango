package com.sebastianvm.mango.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExampleScreen(exampleViewModel: ExampleViewModel, modifier: Modifier = Modifier) {
    val viewState by exampleViewModel.state.collectAsStateWithLifecycle()
    ExampleScreenLayout(
        state = viewState,
        onSubmitJobName = { exampleViewModel.handle(JobNameEntered(it)) },
        modifier = modifier
    )
}

@Composable
fun ExampleScreenLayout(
    state: ExampleState,
    onSubmitJobName: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var jobName by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier.padding(all = 16.dp)) {
        TextField(
            value = jobName,
            onValueChange = { newValue -> jobName = newValue },
            label = {
                Text(text = "Job name")
            }

        )
        Button(onClick = {
            onSubmitJobName(jobName)
            jobName = ""
        }) {
            Text(text = "Add job")
        }

        LazyColumn {
            items(state.jobs) {
                ListItem(headlineContent = { Text(text = it) })
            }
        }
    }
}
