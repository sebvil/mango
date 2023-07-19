package com.sebastianvm.mango.data

import com.sebastianvm.fakegen.FakeClass
import com.sebastianvm.fakegen.FakeCommandMethod
import com.sebastianvm.fakegen.FakeQueryMethod
import com.sebastianvm.mango.database.dao.TaxDao
import com.sebastianvm.mango.database.models.TaxEntity
import com.sebastianvm.mango.model.Fixed
import com.sebastianvm.mango.model.FixedWithMax
import com.sebastianvm.mango.model.Progressive
import com.sebastianvm.mango.model.Tax
import com.sebastianvm.mango.model.Taxes
import com.sebastianvm.mango.util.coroutines.IODispatcher
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import java.math.BigDecimal
import javax.inject.Inject

@FakeClass
interface Calculator {

    @FakeQueryMethod
    suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal
}

class CalculatorImpl @Inject constructor(
    private val taxDao: TaxDao,
    @IODispatcher private val ioDispatcher: CoroutineDispatcher
) : Calculator {

    override suspend fun calculateTax(grossIncome: BigDecimal, tax: Tax): BigDecimal {
        val incomeWithDeductions =
            grossIncome - tax.deductions.fold(BigDecimal(0)) { acc, deduction ->
                acc + deduction.amount
            }
        return when (val taxType = tax.taxType) {
            is Fixed -> incomeWithDeductions * taxType.rate
            is FixedWithMax -> minOf(taxType.maxTaxableIncome, incomeWithDeductions) * taxType.rate
            is Progressive -> {
                taxType.taxBrackets.foldIndexed(BigDecimal(0)) { index, acc, taxBracket ->
                    val minIncome = if (index == 0) 0 else taxType.taxBrackets[index - 1].maxIncome
                    
                }
            }
        }
    }
}

@Module
@InstallIn(ViewModelComponent::class)
abstract class CalculatorModule {

    @Binds
    abstract fun provideUserStore(calculatorImpl: CalculatorImpl): Calculator
}
