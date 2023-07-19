package com.sebastianvm.mango.data

import com.sebastianvm.mango.model.Fixed
import com.sebastianvm.mango.model.FixedWithMax
import com.sebastianvm.mango.model.Progressive
import com.sebastianvm.mango.model.Tax
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import java.math.BigDecimal
import java.math.RoundingMode

interface Calculator {

    suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal
}

class CalculatorImpl : Calculator {

    override suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal {
        val incomeWithDeductions =
            grossIncome - tax.deductions.fold(BigDecimal(0)) { acc, deduction ->
                acc + deduction.amount
            }
        return when (val taxType = tax.taxType) {
            is Fixed -> incomeWithDeductions * taxType.rate
            is FixedWithMax -> minOf(incomeWithDeductions, taxType.maxTaxableIncome) * taxType.rate
            is Progressive -> {
                taxType.taxBrackets.foldIndexed(BigDecimal(0)) { index, acc, taxBracket ->
                    val minIncome =
                        taxType.taxBrackets.getOrNull(index - 1)?.maxIncome ?: BigDecimal(0)
                    if (minIncome > incomeWithDeductions) {
                        acc
                    } else {
                        val maxIncome =
                            incomeWithDeductions.min(taxBracket.maxIncome ?: incomeWithDeductions)
                        acc + (maxIncome - minIncome) * taxBracket.rate
                    }
                }
            }
        }.setScale(2, RoundingMode.HALF_UP)
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CalculatorModule {

    @Binds
    abstract fun provideUserStore(calculatorImpl: CalculatorImpl): Calculator
}
