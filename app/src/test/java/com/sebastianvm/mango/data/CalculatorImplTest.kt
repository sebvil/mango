package com.sebastianvm.mango.data

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.model.PayFrequency
import com.sebastianvm.mango.model.Taxes
import com.sebastianvm.mango.util.date.DateRange
import kotlinx.coroutines.test.runTest
import kotlinx.datetime.LocalDate
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CalculatorImplTest : BaseTest() {

    private val calculator = CalculatorImpl(dispatcher)

    @Test
    fun `calculate tax works`() = testScope.runTest {
        assertThat(
            calculator.calculateTax(
                BigDecimal("130000"),
                Taxes.FederalIncome.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("21276"))

        assertThat(
            calculator.calculateTax(
                BigDecimal("130000"),
                Taxes.Medicare.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("1885"))

        assertThat(
            calculator.calculateTax(
                BigDecimal("130000"),
                Taxes.SocialSecurity.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("8060"))

        assertThat(
            calculator.calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaIncome.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("9195.66"))

        assertThat(
            calculator.calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaSdi.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("1170"))
    }

    @Test
    fun `calculate paycheck tax works`() = testScope.runTest {
        val frequency = PayFrequency.Weekly(
            firstPayPeriod = DateRange(
                LocalDate(2023, 1, 1),
                LocalDate(2023, 1, 15)
            ),
            daysFromEndOfPayPeriodToPayDay = 0
        )
        assertThat(
            calculator.calculatePaycheckTax(
                grossIncome = BigDecimal("130000"),
                paidYearToDate = BigDecimal("0"),
                tax = Taxes.FederalIncome.taxEntity,
                frequency = frequency
            )
        ).isEqualToIgnoringScale(BigDecimal("409.15"))

        assertThat(
            calculator.calculatePaycheckTax(
                BigDecimal("130000"),
                paidYearToDate = BigDecimal("0"),
                Taxes.Medicare.taxEntity,
                frequency = frequency
            )
        ).isEqualToIgnoringScale(BigDecimal("36.25"))

        assertThat(
            calculator.calculatePaycheckTax(
                BigDecimal("130000"),
                paidYearToDate = BigDecimal("0"),
                Taxes.SocialSecurity.taxEntity,
                frequency = frequency
            )
        ).isEqualToIgnoringScale(BigDecimal("155"))

        assertThat(
            calculator.calculatePaycheckTax(
                BigDecimal("130000"),
                paidYearToDate = BigDecimal("0"),
                Taxes.CaliforniaIncome.taxEntity,
                frequency = frequency
            )
        ).isEqualToIgnoringScale(BigDecimal("176.84"))

        assertThat(
            calculator.calculatePaycheckTax(
                BigDecimal("130000"),
                paidYearToDate = BigDecimal("0"),
                Taxes.CaliforniaSdi.taxEntity,
                frequency = frequency
            )
        ).isEqualToIgnoringScale(BigDecimal("22.5"))
    }
}
