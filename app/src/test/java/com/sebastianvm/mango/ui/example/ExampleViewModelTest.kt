package com.sebastianvm.mango.ui.example

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.FakeProvider
import com.sebastianvm.mango.data.FakeIncomeSourceRepository
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class ExampleViewModelTest : BaseTest() {

    private lateinit var incomeSourceRepository: FakeIncomeSourceRepository

    @BeforeEach
    fun beforeEach() {
        incomeSourceRepository = FakeProvider.incomeSourceRepository
    }

    private fun generateViewModel(): ExampleViewModel {
        return viewModelForTests {
            ExampleViewModel(
                initialState = ExampleState(),
                incomeSourceRepository = incomeSourceRepository
            )
        }
    }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#incomeSourceEntityListProvider")
    fun `init sets state correctly and subscribes to changes`(incomeSourceEntityList: List<IncomeSourceEntity>) =
        testScope.runTest {
            with(generateViewModel()) {
                assertThat(state).isEqualTo(ExampleState(incomeSources = listOf("")))
                incomeSourceRepository.getAllIncomeSourcesValue.emit(incomeSourceEntityList)
                assertThat(state).isEqualTo(ExampleState(incomeSources = incomeSourceEntityList.map { it.name }))
            }
        }

    // TODO write IncomeSourceNameEntered test when command fake generation is done
}
