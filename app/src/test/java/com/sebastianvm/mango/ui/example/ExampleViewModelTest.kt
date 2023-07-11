package com.sebastianvm.mango.ui.example

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.FakeProvider
import com.sebastianvm.mango.data.FakeJobRepository
import com.sebastianvm.mango.database.models.JobEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ExampleViewModelTest : BaseTest() {

    private lateinit var jobRepository: FakeJobRepository

    @BeforeEach
    fun beforeEach() {
        jobRepository = FakeProvider.jobRepository
    }

    private fun generateViewModel(): ExampleViewModel {
        return viewModelForTests {
            ExampleViewModel(
                initialState = ExampleState(),
                jobRepository = jobRepository
            )
        }
    }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#jobEntityListProvider")
    fun `init sets state correctly and subscribes to changes`(jobEntityList: List<JobEntity>) =
        testScope.runTest {
            with(generateViewModel()) {
                assertThat(state).isEqualTo(ExampleState(jobs = listOf("")))
                jobRepository.getAllJobsValue.emit(jobEntityList)
                assertThat(state).isEqualTo(ExampleState(jobs = jobEntityList.map { it.name }))
            }
        }

    // TODO write JobNameEntered test when command fake generation is done
}
