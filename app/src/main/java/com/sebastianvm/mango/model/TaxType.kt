package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
sealed interface TaxType

@Serializable
@SerialName("Progressive")
data class Progressive(val taxBrackets: List<TaxBracket>) : TaxType

@Serializable
@SerialName("Fixed")
data class Fixed(val rate: BigDecimalAsString) : TaxType

@Serializable
@SerialName("Fixed with max")
data class FixedWithMax(val rate: BigDecimalAsString, val maxTaxableIncome: BigDecimalAsString) : TaxType
