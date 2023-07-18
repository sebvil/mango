package com.sebastianvm.mango.ui.example

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawingPadding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.systemBarsPadding
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
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.annotation.RootNavGraph

@RootNavGraph(start = true)
@Destination
@Composable
fun ExampleScreen(exampleViewModel: ExampleViewModel = hiltViewModel()) {
    val viewState by exampleViewModel.stateFlow.collectAsStateWithLifecycle()
    ExampleScreenLayout(
        state = viewState,
        onSubmitIncomeSourceName = { exampleViewModel.handle(IncomeSourceNameEntered(it)) },
        modifier = Modifier.statusBarsPadding()
    )
}

@Composable
fun ExampleScreenLayout(
    state: ExampleState,
    onSubmitIncomeSourceName: (text: String) -> Unit,
    modifier: Modifier = Modifier
) {
    var incomeSourceName by remember {
        mutableStateOf("")
    }
    Column(modifier = modifier.padding(horizontal = 16.dp)) {
        TextField(
            value = incomeSourceName,
            onValueChange = { newValue -> incomeSourceName = newValue },
            label = {
                Text(text = "Income source name")
            }

        )
        Button(onClick = {
            onSubmitIncomeSourceName(incomeSourceName)
            incomeSourceName = ""
        }) {
            Text(text = "Add income source")
        }

        LazyColumn(contentPadding = WindowInsets.navigationBars.asPaddingValues()) {
            items(state.incomeSources) {
                ListItem(headlineContent = { Text(text = it) })
            }
        }
    }
}
