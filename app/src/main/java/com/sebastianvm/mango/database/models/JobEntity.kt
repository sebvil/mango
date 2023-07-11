package com.sebastianvm.mango.database.models

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastianvm.mango.model.Job

@Entity
data class JobEntity(
    @PrimaryKey(autoGenerate = true) override val id: Int,
    @ColumnInfo(name = "name") override val name: String
) : Job
