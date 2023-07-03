package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.mango.database.models.JobEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM JobEntity WHERE id = :id LIMIT 1")
    fun getJob(id: Int): Flow<JobEntity>

    @Query("SELECT * FROM JobEntity WHERE id IN (:jobIds)")
    fun loadAll(jobIds: List<Int>): Flow<List<JobEntity>>

    @Insert
    suspend fun insertAll(vararg users: JobEntity)

    @Delete
    suspend fun delete(user: JobEntity)
}
