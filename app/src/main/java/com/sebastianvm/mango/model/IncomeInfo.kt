package com.sebastianvm.mango.model

import kotlinx.datetime.LocalDate

sealed interface IncomeInfo {
    val amount: Float
    val paymentsFrequency: PayFrequency
}

data class Salary(
    override val amount: Float,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

data class Wage(
    override val amount: Float,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

data class OneTime(override val amount: Float, val paymentDate: LocalDate) : IncomeInfo {
    override val paymentsFrequency: PayFrequency = PayFrequency.OneTime(paymentDate)
}