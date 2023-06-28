package com.sebastianvm.mango.ui.example

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import com.sebastianvm.mango.ui.theme.MangoTheme

@Preview(showBackground = true)
@Composable
fun ExampleScreenPreview(@PreviewParameter(ExampleStatePreviewParameterProvider::class) state: ExampleState) {
    MangoTheme {
        ExampleScreenLayout(state = state, onButtonClick = {})
    }
}
