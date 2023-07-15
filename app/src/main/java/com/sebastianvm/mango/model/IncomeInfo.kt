package com.sebastianvm.mango.model

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed interface IncomeInfo {
    val amount: Float
    val paymentsFrequency: PayFrequency
}

@Serializable
data class Salary(
    override val amount: Float,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

@Serializable
data class Wage(
    override val amount: Float,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

@Serializable
data class OneTime(override val amount: Float, val paymentDate: LocalDate) : IncomeInfo {
    override val paymentsFrequency: PayFrequency = PayFrequency.OneTime(paymentDate)
}
