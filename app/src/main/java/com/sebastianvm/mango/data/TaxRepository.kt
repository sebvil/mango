package com.sebastianvm.mango.data

import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.mango.database.dao.TaxDao
import com.sebastianvm.mango.database.models.TaxEntity
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.model.Taxes
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

@FakeClass
interface TaxRepository {

    @FakeCommandMethod
    suspend fun populateTaxes()
}

class TaxRepositoryImpl @Inject constructor(
    private val taxDao: TaxDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : TaxRepository {

    override suspend fun populateTaxes() {
        withContext(ioDispatcher) {
            val taxes = Taxes.values().map { it.taxEntity }
            taxDao.upsertTaxes(taxes)
        }
    }
}

fun Tax.toEnt() = TaxEntity(name, year, jurisdiction, taxType, deductions)

@Module
@InstallIn(ViewModelComponent::class)
abstract class TaxRepositoryModule {

    @Binds
    abstract fun provideUserStore(taxRepositoryImpl: TaxRepositoryImpl): TaxRepository
}
