package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.bigdecimal.BigDecimalAsString
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

@Serializable
sealed interface IncomeInfo {
    val amount: BigDecimalAsString
    val paymentsFrequency: PayFrequency
}

@Serializable
data class Salary(
    override val amount: BigDecimalAsString,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

@Serializable
data class Wage(
    override val amount: BigDecimalAsString,
    override val paymentsFrequency: PayFrequency
) : IncomeInfo

@Serializable
data class OneTime(override val amount: BigDecimalAsString, val paymentDate: LocalDate) : IncomeInfo {
    override val paymentsFrequency: PayFrequency = PayFrequency.OneTime(paymentDate)
}
