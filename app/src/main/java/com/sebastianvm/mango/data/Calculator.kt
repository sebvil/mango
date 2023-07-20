package com.sebastianvm.mango.data

import com.sebastianvm.mango.model.Fixed
import com.sebastianvm.mango.model.FixedWithMax
import com.sebastianvm.mango.model.PayFrequency
import com.sebastianvm.mango.model.Progressive
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.util.coroutines.DefaultDispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import java.math.RoundingMode

interface Calculator {

    suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal
    suspend fun calculatePaycheckTax(
        grossIncome: BigDecimal,
        paidYearToDate: BigDecimal,
        tax: Tax,
        frequency: PayFrequency
    ): BigDecimal

}

class CalculatorImpl(@DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher) :
    Calculator {

    override suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal {
        return withContext(defaultDispatcher) {
            val incomeWithDeductions =
                grossIncome - tax.deductions.fold(BigDecimal(0)) { acc, deduction ->
                    acc + deduction.amount
                }
            when (val taxType = tax.taxType) {
                is Fixed -> incomeWithDeductions * taxType.rate
                is FixedWithMax -> minOf(
                    incomeWithDeductions,
                    taxType.maxTaxableIncome
                ) * taxType.rate

                is Progressive -> {
                    taxType.taxBrackets.foldIndexed(BigDecimal(0)) { index, acc, taxBracket ->
                        val minIncome =
                            taxType.taxBrackets.getOrNull(index - 1)?.maxIncome ?: BigDecimal(0)
                        if (minIncome > incomeWithDeductions) {
                            acc
                        } else {
                            val maxIncome =
                                incomeWithDeductions.min(
                                    taxBracket.maxIncome ?: incomeWithDeductions
                                )
                            acc + (maxIncome - minIncome) * taxBracket.rate
                        }
                    }
                }
            }.setScale(2, RoundingMode.HALF_UP)
        }
    }

    override suspend fun calculatePaycheckTax(
        grossIncome: BigDecimal,
        paidYearToDate: BigDecimal,
        tax: Tax,
        frequency: PayFrequency
    ): BigDecimal {
        val divisor = when (frequency) {
            is PayFrequency.Monthly -> BigDecimal("12")
            is PayFrequency.OneTime -> BigDecimal("1")
            is PayFrequency.SemiMonthly -> BigDecimal("24")
            is PayFrequency.SemiWeekly -> BigDecimal("26")
            is PayFrequency.Weekly -> BigDecimal("52")
        }

        return withContext(defaultDispatcher) {
            when (val taxType = tax.taxType) {
                is Fixed -> calculateTax(grossIncome, tax) / divisor
                is FixedWithMax -> {
                    val maxTax = taxType.rate * taxType.maxTaxableIncome
                    if (maxTax <= paidYearToDate) {
                        BigDecimal("0")
                    } else {
                        val incomeWithDeductions =
                            grossIncome - tax.deductions.fold(BigDecimal(0)) { acc, deduction ->
                                acc + deduction.amount
                            }
                        val fullTax = incomeWithDeductions * taxType.rate
                        minOf(fullTax, maxTax - paidYearToDate) / divisor
                    }
                }
                is Progressive -> calculateTax(grossIncome, tax) / divisor
            }.setScale(2, RoundingMode.HALF_UP)
        }
    }

}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CalculatorModule {

    @Binds
    abstract fun provideUserStore(calculatorImpl: CalculatorImpl): Calculator
}
