package com.sebastianvm.mango.model

import androidx.annotation.StringRes
import com.sebastianvm.mango.R
import com.sebastianvm.mango.database.models.TaxEntity
import java.math.BigDecimal

enum class Taxes(@StringRes val taxName: Int, val taxEntity: TaxEntity) {
    FederalIncome(
        taxName = R.string.federal_tax,
        taxEntity = TaxEntity(
            name = "Federal income",
            jurisdiction = Jurisdiction.Federal,
            year = 2023,
            taxType = Progressive(
                taxBrackets = listOf(
                    TaxBracket(rate = BigDecimal("0.1"), maxIncome = BigDecimal("11000")),
                    TaxBracket(rate = BigDecimal("0.12"), maxIncome = BigDecimal("44725")),
                    TaxBracket(rate = BigDecimal("0.24"), maxIncome = BigDecimal("95375")),
                    TaxBracket(rate = BigDecimal("0.32"), maxIncome = BigDecimal("182100")),
                    TaxBracket(rate = BigDecimal("0.35"), maxIncome = BigDecimal("231250")),
                    TaxBracket(rate = BigDecimal("0.37"), maxIncome = null)
                )
            ),
            deductions = listOf(
                Deduction(
                    name = "Standard federal deduction",
                    amount = BigDecimal("13850")
                )
            )
        )
    ),
    Medicare(
        taxName = R.string.medicare,
        taxEntity = TaxEntity(
            name = "Medicare",
            jurisdiction = Jurisdiction.Federal,
            year = 2023,
            taxType = Fixed(rate = BigDecimal("0.0145")),
            deductions = listOf()
        )
    ),
    SocialSecurity(
        taxName = R.string.social_security,
        taxEntity = TaxEntity(
            name = "Social security",
            jurisdiction = Jurisdiction.Federal,
            year = 2023,
            taxType = FixedWithMax(
                rate = BigDecimal("0.0145"),
                maxTaxableIncome = BigDecimal("160200")
            ),
            deductions = listOf()
        )
    )
}
