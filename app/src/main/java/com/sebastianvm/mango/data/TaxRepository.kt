package com.sebastianvm.mango.data

import android.content.Context
import androidx.annotation.RawRes
import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.mango.R
import com.sebastianvm.mango.database.dao.TaxDao
import com.sebastianvm.mango.database.models.TaxEntity
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import kotlinx.serialization.ExperimentalSerializationApi
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.decodeFromStream
import javax.inject.Inject

@FakeClass
interface TaxRepository {

    @FakeCommandMethod
    suspend fun populateTaxes()
}

class TaxRepositoryImpl @Inject constructor(
    @ApplicationContext private val context: Context,
    private val taxDao: TaxDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : TaxRepository {

    override suspend fun populateTaxes() {
        withContext(ioDispatcher) {
            val federalTaxes2023 = readRawJsonFile<TaxEntity>(R.raw.federal_tax_2023)
            taxDao.upsertTaxes(listOf(federalTaxes2023))
        }
    }

    private val json = Json { isLenient = true }

    @OptIn(ExperimentalSerializationApi::class)
    private inline fun <reified T> readRawJsonFile(@RawRes res: Int): T {
        return json.decodeFromStream(context.resources.openRawResource(res))
    }
}

fun Tax.toEnt() = TaxEntity(name, year, jurisdiction, taxType, deductions)

@Module
@InstallIn(ViewModelComponent::class)
abstract class TaxRepositoryModule {

    @Binds
    abstract fun provideUserStore(taxRepositoryImpl: TaxRepositoryImpl): TaxRepository
}
