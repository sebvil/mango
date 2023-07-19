package com.sebastianvm.mango.data

import com.google.common.truth.Truth.assertThat
import com.sebastianvm.mango.BaseTest
import com.sebastianvm.mango.model.Taxes
import kotlinx.coroutines.test.runTest
import org.junit.jupiter.api.Test
import java.math.BigDecimal

class CalculatorImplTest : BaseTest() {

    @Test
    fun `calculate tax works`() = testScope.runTest {
        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.FederalIncome.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("21276"))

        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.Medicare.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("1885"))

        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.SocialSecurity.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("8060"))

        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaIncome.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("9195.66"))

        assertThat(
            CalculatorImpl().calculateTax(
                BigDecimal("130000"),
                Taxes.CaliforniaSdi.taxEntity
            )
        ).isEqualToIgnoringScale(BigDecimal("1170"))
    }
}
