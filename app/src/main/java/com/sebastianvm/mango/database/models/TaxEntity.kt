package com.sebastianvm.mango.database.models

import androidx.room.Entity
import com.sebastianvm.mango.model.Deduction
import com.sebastianvm.mango.model.Jurisdiction
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.model.TaxType

@Entity(primaryKeys = ["name", "year"])
data class TaxEntity(
    override val name: String,
    override val year: Int,
    override val jurisdiction: Jurisdiction,
    override val taxType: TaxType,
    override val deductions: List<Deduction>
) : Tax
