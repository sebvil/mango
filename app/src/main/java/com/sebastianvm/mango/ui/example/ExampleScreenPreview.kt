package com.sebastianvm.mango.ui.example

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sebastianvm.mango.ui.util.preview.ScreenPreview

@ScreenPreview
@Composable
fun ExampleScreenPreview(@PreviewParameter(ExampleStatePreviewParameterProvider::class) state: ExampleState) {
    ScreenPreview {
        ExampleScreenLayout(state = state, onButtonClick = {})
    }
}
