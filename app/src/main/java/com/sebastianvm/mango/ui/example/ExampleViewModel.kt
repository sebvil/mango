package com.sebastianvm.mango.ui.example

import com.sebastianvm.mango.data.IncomeSourceRepository
import com.sebastianvm.mango.ui.mvvm.BaseViewModel
import com.sebastianvm.mango.ui.mvvm.State
import com.sebastianvm.mango.ui.mvvm.UserAction
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

data class ExampleState(
    val incomeSources: List<String> = listOf()
) : State

sealed interface ExampleUserAction : UserAction
data class IncomeSourceNameEntered(val incomeSourceName: String) : ExampleUserAction

@HiltViewModel
class ExampleViewModel @Inject constructor(
    initialState: ExampleState,
    private val incomeSourceRepository: IncomeSourceRepository
) : BaseViewModel<ExampleState, ExampleUserAction>(initialState) {

    init {
        incomeSourceRepository.getAllIncomeSources().onEach { incomeSources ->
            setState { copy(incomeSources = incomeSources.map { it.name }) }
        }.launchIn(vmScope)
    }

    override fun handle(action: ExampleUserAction) {
        when (action) {
            is IncomeSourceNameEntered -> {
                vmScope.launch {
                    incomeSourceRepository.createIncomeSource(action.incomeSourceName)
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
