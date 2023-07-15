package com.sebastianvm.mango.model

sealed interface TaxType

data class Progressive(val taxBrackets: List<TaxBracket>)
data class Fixed(val rate: Float)
data class FixedWithMax(val rate: Float, val maxTaxableIncome: Float)
