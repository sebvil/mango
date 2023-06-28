package com.sebastianvm.mango.ui.example

import androidx.lifecycle.SavedStateHandle
import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import com.sebastianvm.mango.ui.mvvm.State
import com.sebastianvm.mango.ui.mvvm.UserAction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import javax.inject.Inject

data class ExampleState(
    val displayText: String = "Placeholder",
    val rows: List<String> = (0 until 10).map { "Row $it" }
) : State

sealed interface ExampleUserAction : UserAction
data class DidTapItem(val rowText: String) : ExampleUserAction


@HiltViewModel
class ExampleViewModel @Inject constructor(initialState: ExampleState) :
    BaseViewModel<ExampleState, ExampleUserAction>(initialState) {
    override fun handle(action: ExampleUserAction) {
        when (action) {
            is DidTapItem -> {
                setState { copy(displayText = action.rowText) }
            }
        }
    }
}


@InstallIn(ViewModelComponent::class)
@Module
object InitialExampleStateModule {

    @Provides
    @ViewModelScoped
    fun initialExampleStateProvider(savedStateHandle: SavedStateHandle): ExampleState {
        return ExampleState()
    }
}
