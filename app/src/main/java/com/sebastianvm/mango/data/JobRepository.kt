package com.sebastianvm.mango.data

import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.dao.JobDao
import com.sebastianvm.mango.database.models.JobEntity
import com.sebastianvm.mango.model.Job
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@FakeClass
interface JobRepository {

    @FakeQueryMethod
    fun getJob(id: Int): Flow<Job>

    @FakeQueryMethod
    fun loadAll(jobIds: List<Int>): Flow<List<Job>>
}

class JobRepositoryImpl @Inject constructor(
    private val jobDao: JobDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : JobRepository {
    override fun getJob(id: Int): Flow<Job> {
        return jobDao.getJob(id).flowOn(ioDispatcher)
    }

    override fun loadAll(jobIds: List<Int>): Flow<List<Job>> {
        return jobDao.loadAll(jobIds)
    }
}

fun Job.toEnt() = JobEntity(id, name)

@Module
@InstallIn(ViewModelComponent::class)
abstract class JobRepositoryModule {

    @Binds
    abstract fun provideUserStore(jobRepositoryImpl: JobRepositoryImpl): JobRepository
}
