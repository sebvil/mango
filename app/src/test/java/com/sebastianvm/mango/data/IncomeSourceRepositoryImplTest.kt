package com.sebastianvm.mango.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.FakeProvider
import com.sebastianvm.mango.database.dao.FakeIncomeSourceDao
import com.sebastianvm.mango.database.dao.FakeIncomeSourceDaoImpl
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class IncomeSourceRepositoryImplTest : BaseTest() {

    private lateinit var incomeSourceDao: FakeIncomeSourceDaoImpl

    @BeforeEach
    fun beforeEach() {
        incomeSourceDao = FakeProvider.incomeSourceDao
    }

    private fun getRepository(): IncomeSourceRepositoryImpl {
        return IncomeSourceRepositoryImpl(incomeSourceDao = incomeSourceDao, ioDispatcher = dispatcher)
    }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#incomeSourceEntityProvider")
    fun `getIncomeSource returns and subscribes only to new changes in incomeSourceDao's incomeSource`(incomeSourceEntity: IncomeSourceEntity) =
        testScope.runTest {
            getRepository().getIncomeSource(id = 0).test {
                assertThat(awaitItem()).isEqualTo(FakeProvider.defaultIncomeSourceEntity)
                incomeSourceDao.getIncomeSourceValue.emit(incomeSourceEntity)
                assertThat(awaitItem()).isEqualTo(incomeSourceEntity)

                // This verifies that we don't emit a new value if the value
                // emitted by the DAO has not changed
                incomeSourceDao.getIncomeSourceValue.emit(incomeSourceEntity)
            }
        }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#incomeSourceEntityListProvider")
    fun `loadAll returns and subscribes only to new changes incomeSourceDao's loadAll`(incomeSourceEntityList: List<IncomeSourceEntity>) =
        testScope.runTest {
            getRepository().getAllIncomeSources().test {
                assertThat(awaitItem()).isEqualTo(listOf(FakeProvider.defaultIncomeSourceEntity))
                incomeSourceDao.getAllIncomeSourcesValue.emit(incomeSourceEntityList)
                assertThat(awaitItem()).isEqualTo(incomeSourceEntityList)

                // This verifies that we don't emit a new value if the value
                // emitted by the DAO has not changed
                incomeSourceDao.getAllIncomeSourcesValue.emit(incomeSourceEntityList)
            }
        }

    @Test
    fun `insertIncomeSource calls incomeSourceDao's createIncomeSource`() = testScope.runTest {
        val repository = getRepository()
        val incomeSourceName = "incomeSourceName"
        repository.createIncomeSource(incomeSourceName = incomeSourceName)
        assertThat(incomeSourceDao.insertIncomeSourceInvocations.last()).isEqualTo(listOf(IncomeSourceEntity(id = 0, name = incomeSourceName)))
    }
}
