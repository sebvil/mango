package com.sebastianvm.mango.data

import com.google.common.truth.Truth.assertThat
import org.junit.jupiter.api.Assertions.*

import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.model.Taxes
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.math.BigDecimal
import java.math.RoundingMode

class CalculatorImplTest : BaseTest() {


    @Test
    fun `calculate tax works`() = testScope.runTest {
        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.FederalIncome.taxEntity
            ) / BigDecimal(24)
        ).isEqualTo(BigDecimal("886.50"))

        assertThat(
            (CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.Medicare.taxEntity
            ) / BigDecimal(24)).setScale(2, RoundingMode.HALF_UP)
        ).isEqualTo(BigDecimal("78.54"))

        assertThat(
            (CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.SocialSecurity.taxEntity
            ) / BigDecimal(24)).setScale(2, RoundingMode.HALF_UP)
        ).isEqualTo(BigDecimal("335.83"))

        assertThat(
            (CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaIncome.taxEntity
            ) / BigDecimal(24)).setScale(2, RoundingMode.HALF_UP)
        ).isEqualTo(BigDecimal("383.15"))

        assertThat(
            (CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaSdi.taxEntity
            ) / BigDecimal(24)).setScale(2, RoundingMode.HALF_UP)
        ).isEqualTo(BigDecimal("48.75"))
    }
}