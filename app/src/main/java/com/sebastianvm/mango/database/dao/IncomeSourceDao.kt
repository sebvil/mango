package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.models.IncomeSourceEntity
import kotlinx.coroutines.flow.Flow

@FakeClass
@Dao
interface IncomeSourceDao {
    @FakeQueryMethod
    @Query("SELECT * FROM IncomeSourceEntity WHERE id = :id LIMIT 1")
    fun getIncomeSource(id: Int): Flow<IncomeSourceEntity>

    @FakeQueryMethod
    @Query("SELECT * FROM IncomeSourceEntity")
    fun getAllIncomeSources(): Flow<List<IncomeSourceEntity>>

    @FakeCommandMethod
    @Insert
    suspend fun insertIncomeSource(incomeSource: IncomeSourceEntity)

    @FakeCommandMethod
    @Delete
    suspend fun delete(incomeSource: IncomeSourceEntity)
}
