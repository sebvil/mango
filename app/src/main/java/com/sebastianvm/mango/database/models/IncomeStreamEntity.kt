package com.sebastianvm.mango.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastianvm.mango.model.IncomeInfo
import com.sebastianvm.mango.model.IncomeStream


@Entity
data class IncomeStreamEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    override val name: String,
    override val type: IncomeInfo,
    override val paidToSelf: Boolean
) : IncomeStream