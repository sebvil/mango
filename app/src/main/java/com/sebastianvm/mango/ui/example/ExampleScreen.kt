package com.sebastianvm.mango.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun ExampleScreen(modifier: Modifier = Modifier, exampleViewModel: ExampleViewModel = viewModel()) {
    val viewState = exampleViewModel.state.collectAsState()

    Column {
        Text(text = viewState.value.displayText, modifier)

        viewState.value.rows.map { 
            Row {
                Button(onClick = { exampleViewModel.handle(DidTapItem(it)) }, modifier) {
                    Text(it, modifier)
                }
            }
        }
    }
}