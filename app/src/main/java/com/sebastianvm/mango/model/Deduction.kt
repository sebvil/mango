package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.serialization.Serializable

@Serializable
data class Deduction(
    val name: String,
    val amount: BigDecimalAsString
)
