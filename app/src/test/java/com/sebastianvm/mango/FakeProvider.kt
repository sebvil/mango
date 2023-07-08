package com.sebastianvm.mango

import com.sebastianvm.mango.database.dao.FakeJobDao
import com.sebastianvm.mango.database.models.JobEntity
import kotlinx.coroutines.flow.MutableStateFlow
import java.util.stream.Stream

object FakeProvider {

    val jobDao: FakeJobDao
        get() = FakeJobDao(
            getJobValue = MutableStateFlow(JobEntity(id = 0, name = "")),
            loadAllValue = MutableStateFlow(listOf(JobEntity(id = 0, name = "")))
        )

    @JvmStatic
    fun jobEntityProvider(): Stream<JobEntity> {
        return Stream.of(
            JobEntity(id = 0, name = "Mango"),
            JobEntity(id = 1, name = "Nectarine"),
            JobEntity(id = 2, name = "Orange")
        )
    }

    @JvmStatic
    fun jobEntityListProvider(): Stream<List<JobEntity>> {
        return Stream.of(
            listOf(
                JobEntity(id = 0, name = "Mango"),
            ),
            listOf(
                JobEntity(id = 0, name = "Mango"),
                JobEntity(id = 1, name = "Nectarine"),
            ),
            listOf(
                JobEntity(id = 0, name = "Mango"),
                JobEntity(id = 1, name = "Nectarine"),
                JobEntity(id = 2, name = "Orange")
            ),
        )
    }
}
