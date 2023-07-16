package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.serialization.Serializable

@Serializable
data class TaxBracket(val rate: BigDecimalAsString, val maxIncome: Float)
