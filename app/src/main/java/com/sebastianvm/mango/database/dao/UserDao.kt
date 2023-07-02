package com.sebastianvm.mango.database.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.sebastianvm.mango.database.models.UserEnt

@Dao
interface UserDao {
    @Query("SELECT * FROM UserEnt")
    suspend fun getAll(): List<UserEnt>

    @Query("SELECT * FROM UserEnt WHERE id = :id LIMIT 1")
    suspend fun getUserById(id: Int): UserEnt?

    @Query("SELECT * FROM UserEnt WHERE id IN (:userIds)")
    suspend fun loadAllByIds(userIds: IntArray): List<UserEnt>

    @Insert
    suspend fun insertAll(vararg users: UserEnt)

    @Delete
    suspend fun delete(user: UserEnt)
}
