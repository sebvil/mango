package com.sebastianvm.mango.model

import kotlinx.serialization.Serializable

@Serializable
data class TaxBracket(val rate: Float, val maxIncome: Float)
