package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.serialization.Serializable

@Serializable
sealed interface TaxType

@Serializable
data class Progressive(val taxBrackets: List<TaxBracket>)

@Serializable
data class Fixed(val rate: BigDecimalAsString)

@Serializable
data class FixedWithMax(val rate: BigDecimalAsString, val maxTaxableIncome: BigDecimalAsString)
