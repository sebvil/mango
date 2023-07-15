package com.sebastianvm.mango.data

import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.dao.IncomeSourceDao
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import com.sebastianvm.mango.model.IncomeSource
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

@FakeClass
interface IncomeSourceRepository {

    @FakeQueryMethod
    fun getIncomeSource(id: Int): Flow<IncomeSource>

    @FakeQueryMethod
    fun getAllIncomeSources(): Flow<List<IncomeSource>>

    @FakeCommandMethod
    suspend fun createIncomeSource(incomeSourceName: String)
}

class IncomeSourceRepositoryImpl @Inject constructor(
    private val incomeSourceDao: IncomeSourceDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : IncomeSourceRepository {
    override fun getIncomeSource(id: Int): Flow<IncomeSource> {
        return incomeSourceDao.getIncomeSource(id).distinctUntilChanged().flowOn(ioDispatcher)
    }

    override fun getAllIncomeSources(): Flow<List<IncomeSource>> {
        return incomeSourceDao.getAllIncomeSources().distinctUntilChanged().flowOn(ioDispatcher)
    }

    override suspend fun createIncomeSource(incomeSourceName: String) {
        incomeSourceDao.insertIncomeSource(IncomeSourceEntity(id = 0, name = incomeSourceName))
    }
}

fun IncomeSource.toEnt() = IncomeSourceEntity(id, name)

@Module
@InstallIn(ViewModelComponent::class)
abstract class IncomeSourceRepositoryModule {

    @Binds
    abstract fun provideUserStore(incomeSourceRepositoryImpl: IncomeSourceRepositoryImpl): IncomeSourceRepository
}
