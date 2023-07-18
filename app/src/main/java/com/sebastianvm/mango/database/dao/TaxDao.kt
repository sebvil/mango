package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Upsert
import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import com.sebastianvm.mango.database.models.TaxEntity
import kotlinx.coroutines.flow.Flow

@FakeClass
@Dao
interface TaxDao {

    @FakeCommandMethod
    @Upsert
    suspend fun upsertTaxes(taxes: List<TaxEntity>)

}
