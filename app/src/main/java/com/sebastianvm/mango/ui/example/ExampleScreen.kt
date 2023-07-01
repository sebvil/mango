package com.sebastianvm.mango.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.compose.collectAsStateWithLifecycle

@Composable
fun ExampleScreen(exampleViewModel: ExampleViewModel, modifier: Modifier = Modifier) {
    val viewState by exampleViewModel.state.collectAsStateWithLifecycle()
    ExampleScreenLayout(
        state = viewState,
        onButtonClick = { exampleViewModel.handle(DidTapItem(it)) },
        modifier = modifier
    )
}

@Composable
fun ExampleScreenLayout(
    state: ExampleState,
    onButtonClick: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(modifier = modifier) {
        Text(text = state.displayText)

        state.rows.map {
            Row {
                Button(onClick = { onButtonClick(it) }) {
                    Text(it)
                }
            }
        }
    }
}
