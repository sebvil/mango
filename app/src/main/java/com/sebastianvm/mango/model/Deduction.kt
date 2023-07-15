package com.sebastianvm.mango.model

import kotlinx.serialization.Serializable

@Serializable
data class Deduction(val name: String, val amount: Int)
