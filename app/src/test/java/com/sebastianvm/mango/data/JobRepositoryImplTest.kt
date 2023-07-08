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

    @ParameterizedTest()
    @MethodSource("com.sebastianvm.mango.FakeProvider#jobEntityProvider")
    fun `getJob returns jobDao's job`(jobEntity: JobEntity) = testScope.runTest {
        jobDao.getJobValue.value = jobEntity
        val jobRepository = JobRepositoryImpl(jobDao = jobDao, ioDispatcher = dispatcher)
        jobRepository.getJob(0).test {
            assertThat(awaitItem()).isEqualTo(jobEntity)
        }
    }
}
