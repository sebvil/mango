package com.sebastianvm.mango.ui.util.preview

import android.content.res.Configuration
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.sebastianvm.mango.ui.theme.MangoTheme

@Composable
fun ComponentPreview(
    content: @Composable () -> Unit
) {
    MangoTheme {
        content()
    }
}

@Composable
fun ScreenPreview(screen: @Composable () -> Unit) {
    MangoTheme {
        // A surface container using the 'background' color from the theme
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            screen()
        }

    }
}

@Preview(
    name = "portrait, light",
    showSystemUi = true
)
@Preview(
    name = "portrait, dark ",
    uiMode = Configuration.UI_MODE_NIGHT_YES, showSystemUi = true
)
@Preview(
    name = "Landscape, light",
    device = "spec:parent=pixel_6,orientation=landscape",
    showSystemUi = true
)
@Preview(
    name = "Landscape, dark",
    uiMode = Configuration.UI_MODE_NIGHT_YES,
    device = "spec:parent=pixel_6,orientation=landscape",
    showSystemUi = true
)
annotation class ScreenPreview

@Preview
@Preview(uiMode = Configuration.UI_MODE_NIGHT_YES)
annotation class ComponentPreview