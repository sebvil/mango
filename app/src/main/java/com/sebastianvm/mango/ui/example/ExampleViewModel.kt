package com.sebastianvm.mango.ui.example

import androidx.lifecycle.viewModelScope
import com.sebastianvm.mango.data.UserStore
import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import com.sebastianvm.mango.ui.mvvm.State
import com.sebastianvm.mango.ui.mvvm.UserAction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExampleState(
    val displayText: String = "Placeholder",
    val rows: List<String> = (0 until 10).map { "Row $it" }
) : State

sealed interface ExampleUserAction : UserAction
data class DidTapItem(val rowText: String) : ExampleUserAction


@HiltViewModel
class ExampleViewModel @Inject constructor(
    initialState: ExampleState,
    private val userStore: UserStore) :
    BaseViewModel<ExampleState, ExampleUserAction>(initialState) {
    override fun handle(action: ExampleUserAction) {
        when (action) {
            is DidTapItem -> {
                setState { copy(displayText = action.rowText) }
                // no-op, user doesn't exist, using for purposes of example
                viewModelScope.launch {
                    val user = userStore.getUserById(1)
                    user?.let { setState { copy(displayText = it.name ?: "NO NAME") } }
                }
            }
        }
    }
}


@InstallIn(ViewModelComponent::class)
@Module
object InitialExampleStateModule {

    @Provides
    @ViewModelScoped
    fun initialExampleStateProvider(): ExampleState {
        return ExampleState()
    }
}
