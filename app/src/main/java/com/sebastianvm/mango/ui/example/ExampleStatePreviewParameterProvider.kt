package com.sebastianvm.mango.ui.example

import androidx.compose.ui.tooling.preview.PreviewParameterProvider

class ExampleStatePreviewParameterProvider : PreviewParameterProvider<ExampleState> {
    override val values: Sequence<ExampleState>
        get() = sequenceOf(
            ExampleState(jobs = listOf("Hello, world", "Goodbye, world")),
            ExampleState(jobs = listOf("Compose < Views", "Compose == Views", "Compose > Views"))
        )
}
