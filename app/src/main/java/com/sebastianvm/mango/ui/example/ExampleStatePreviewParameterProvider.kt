package com.sebastianvm.mango.ui.example

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ExampleStatePreviewParameterProvider : PreviewParameterProvider<ExampleState> {
    override val values: Sequence<ExampleState>
        get() = sequenceOf(
            ExampleState(displayText = "Hello, world", rows = listOf("Hello, world", "Goodbye, world",)),
            ExampleState(displayText = "Compose > Views", rows = listOf("Compose < Views", "Compose == Views","Compose > Views")),
        )
}