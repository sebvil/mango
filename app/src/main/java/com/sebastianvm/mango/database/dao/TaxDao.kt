package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Upsert
import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.mango.database.models.TaxEntity

@FakeClass
@Dao
interface TaxDao {

    @FakeCommandMethod
    @Upsert
    suspend fun upsertTaxes(taxes: List<TaxEntity>)
}
