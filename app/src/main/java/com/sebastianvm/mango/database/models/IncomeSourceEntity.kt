package com.sebastianvm.mango.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastianvm.mango.model.IncomeSource

@Entity
data class IncomeSourceEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    override val name: String
) : IncomeSource
