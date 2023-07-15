package com.sebastianvm.mango.model

import kotlinx.serialization.Serializable

@Serializable
sealed interface TaxType

@Serializable
data class Progressive(val taxBrackets: List<TaxBracket>)

@Serializable
data class Fixed(val rate: Float)

@Serializable
data class FixedWithMax(val rate: Float, val maxTaxableIncome: Float)
