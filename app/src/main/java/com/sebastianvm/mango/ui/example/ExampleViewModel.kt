package com.sebastianvm.mango.ui.example

import com.sebastianvm.mango.data.JobRepository
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
    val jobs: List<String> = listOf()
) : State

sealed interface ExampleUserAction : UserAction
data class JobNameEntered(val jobName: String) : ExampleUserAction

@HiltViewModel
class ExampleViewModel @Inject constructor(
    initialState: ExampleState,
    private val jobRepository: JobRepository
) : BaseViewModel<ExampleState, ExampleUserAction>(initialState) {

    init {
        jobRepository.getAllJobs().onEach { jobs ->
            setState { copy(jobs = jobs.map { it.name }) }
        }.launchIn(vmScope)
    }

    override fun handle(action: ExampleUserAction) {
        when (action) {
            is JobNameEntered -> {
                vmScope.launch {
                    jobRepository.createJob(action.jobName)
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
