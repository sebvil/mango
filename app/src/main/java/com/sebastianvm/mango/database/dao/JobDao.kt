package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.mango.database.MangoDatabase
import com.sebastianvm.mango.database.models.JobEntity
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.flow.Flow

@Dao
interface JobDao {
    @Query("SELECT * FROM JobEntity WHERE id = :id LIMIT 1")
    fun getJobById(id: Int): Flow<JobEntity>

    @Query("SELECT * FROM JobEntity WHERE id IN (:jobIds)")
    fun loadAllByIds(jobIds: IntArray): Flow<List<JobEntity>>

    @Insert
    suspend fun insertAll(vararg users: JobEntity)

    @Delete
    suspend fun delete(user: JobEntity)
}
