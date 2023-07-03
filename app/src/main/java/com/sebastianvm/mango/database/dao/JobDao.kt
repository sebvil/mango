package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.mango.database.models.JobEnt
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM JobEnt WHERE id = :id LIMIT 1")
    fun getJobById(id: Int): Flow<JobEnt>

    @Query("SELECT * FROM JobEnt WHERE id IN (:jobIds)")
    fun loadAllByIds(jobIds: IntArray): Flow<List<JobEnt>>

    @Insert
    suspend fun insertAll(vararg users: JobEnt)

    @Delete
    suspend fun delete(user: JobEnt)
}
