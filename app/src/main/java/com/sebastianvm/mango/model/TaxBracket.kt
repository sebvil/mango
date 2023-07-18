package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.serialization.Serializable

/**
 * Null [maxIncome] represents infinity
 */
@Serializable
data class TaxBracket(val rate: BigDecimalAsString, val maxIncome: BigDecimalAsString?)
