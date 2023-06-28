package com.sebastianvm.mango.ui.example

import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import com.sebastianvm.mango.ui.mvvm.State
import com.sebastianvm.mango.ui.mvvm.UserAction

data class ExampleState(
    val displayText: String = "Placeholder",
    val rows: List<String> = (0 until 10).map { "Row $it" }
) : State

sealed interface ExampleUserAction : UserAction
data class DidTapItem(val rowText: String) : ExampleUserAction

class ExampleViewModel(
    initialState: ExampleState = ExampleState()
) : BaseViewModel<ExampleState, ExampleUserAction>(initialState) {
    override suspend fun onAction(action: ExampleUserAction) {
        when (action) {
            is DidTapItem -> {
                setState { copy(displayText = action.rowText) }
            }
        }
    }
}
