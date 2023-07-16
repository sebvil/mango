package com.sebastianvm.mango.database.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.sebastianvm.mango.model.Deduction
import com.sebastianvm.mango.model.Jurisdiction
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.model.TaxType

@Entity
data class TaxEntity(
    @PrimaryKey(autoGenerate = true) override val id: Long,
    override val name: String,
    override val jurisdiction: Jurisdiction,
    override val taxType: TaxType,
    override val deductions: List<Deduction>
) : Tax
