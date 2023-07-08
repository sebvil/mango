package com.sebastianvm.mango.data

import app.cash.turbine.test
import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.FakeProvider
import com.sebastianvm.mango.database.dao.FakeJobDao
import com.sebastianvm.mango.database.models.JobEntity
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.MethodSource

class JobRepositoryImplTest : BaseTest() {

    private lateinit var jobDao: FakeJobDao

    @BeforeEach
    fun beforeEach() {
        jobDao = FakeProvider.jobDao
    }

    private fun getRepository(): JobRepositoryImpl {
        return JobRepositoryImpl(jobDao = jobDao, ioDispatcher = dispatcher)
    }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#jobEntityProvider")
    fun `getJob returns jobDao's job`(jobEntity: JobEntity) = testScope.runTest {
        jobDao.getJobValue.value = jobEntity
        getRepository().getJob(id = 0).test {
            assertThat(awaitItem()).isEqualTo(jobEntity)
        }
    }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#jobEntityListProvider")
    fun `loadAll returns jobDao's job`(jobEntityList: List<JobEntity>) = testScope.runTest {
        jobDao.loadAllValue.value = jobEntityList
        getRepository().loadAll(jobIds = listOf()).test {
            assertThat(awaitItem()).isEqualTo(jobEntityList)
        }
    }
}
