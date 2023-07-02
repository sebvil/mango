package com.sebastianvm.mango.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastianvm.mango.model.User

@Entity
data class UserEnt(
    @PrimaryKey override val id: Int,
    @ColumnInfo(name = "name") override val name: String?,
) : User