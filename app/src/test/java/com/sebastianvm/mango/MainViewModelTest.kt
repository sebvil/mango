package com.sebastianvm.mango

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.data.FakeTaxRepository
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class MainViewModelTest : BaseTest() {

    private lateinit var taxRepository: FakeTaxRepository

    @BeforeEach
    fun beforeEach() {
        taxRepository = FakeProvider.taxRepository
    }

    private fun generateViewModel(): MainViewModel {
        return viewModelForTests { MainViewModel(taxRepository = taxRepository) }
    }

    @Test
    fun `handle InitDatabase populates taxes`() = testScope.runTest {
        with(generateViewModel()) {
            handle(InitDatabase)
            assertThat(taxRepository.populateTaxesInvocations).containsExactly(listOf<Any>())
        }
    }
}
