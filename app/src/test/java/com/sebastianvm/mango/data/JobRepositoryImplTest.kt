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
    fun `getJob returns and subscribes only to new changes in jobDao's job`(jobEntity: JobEntity) =
        testScope.runTest {
            getRepository().getJob(id = 0).test {
                assertThat(awaitItem()).isEqualTo(FakeProvider.defaultJobEntity)
                jobDao.getJobValue.emit(jobEntity)
                assertThat(awaitItem()).isEqualTo(jobEntity)

                // This verifies that we don't emit a new value if the value
                // emitted by the DAO has not changed
                jobDao.getJobValue.emit(jobEntity)
            }
        }

    @ParameterizedTest
    @MethodSource("com.sebastianvm.mango.FakeProvider#jobEntityListProvider")
    fun `loadAll returns and subscribes only to new changes jobDao's loadAll`(jobEntityList: List<JobEntity>) =
        testScope.runTest {
            getRepository().getAllJobs().test {
                assertThat(awaitItem()).isEqualTo(listOf(FakeProvider.defaultJobEntity))
                jobDao.getAllJobsValue.emit(jobEntityList)
                assertThat(awaitItem()).isEqualTo(jobEntityList)

                // This verifies that we don't emit a new value if the value
                // emitted by the DAO has not changed
                jobDao.getAllJobsValue.emit(jobEntityList)
            }
        }
}
