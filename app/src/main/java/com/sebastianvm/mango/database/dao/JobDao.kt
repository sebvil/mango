package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.models.JobEntity
import kotlinx.coroutines.flow.Flow

@FakeClass
@Dao
interface JobDao {
    @FakeQueryMethod
    @Query("SELECT * FROM JobEntity WHERE id = :id LIMIT 1")
    fun getJob(id: Int): Flow<JobEntity>

    @FakeQueryMethod
    @Query("SELECT * FROM JobEntity WHERE id IN (:jobIds)")
    fun loadAll(jobIds: List<Int>): Flow<List<JobEntity>>

    @FakeCommandMethod
    @Insert
    suspend fun insertAll(users: List<JobEntity>)

    @FakeCommandMethod
    @Delete
    suspend fun delete(user: JobEntity)
}
