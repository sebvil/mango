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
                    TaxBracket(rate = BigDecimal("0.22"), maxIncome = BigDecimal("95375")),
                    TaxBracket(rate = BigDecimal("0.24"), maxIncome = BigDecimal("182100")),
                    TaxBracket(rate = BigDecimal("0.32"), maxIncome = BigDecimal("231250")),
                    TaxBracket(rate = BigDecimal("0.35"), maxIncome = BigDecimal("578125")),
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
                rate = BigDecimal("0.062"),
                maxTaxableIncome = BigDecimal("160200")
            ),
            deductions = listOf()
        )
    ),
    CaliforniaIncome(
        taxName = R.string.california_income,
        taxEntity = TaxEntity(
            name = "California income",
            jurisdiction = Jurisdiction.California,
            year = 2023,
            taxType = Progressive(
                taxBrackets = listOf(
                    TaxBracket(rate = BigDecimal("0.011"), maxIncome = BigDecimal("10099")),
                    TaxBracket(rate = BigDecimal("0.022"), maxIncome = BigDecimal("23942")),
                    TaxBracket(rate = BigDecimal("0.044"), maxIncome = BigDecimal("37788")),
                    TaxBracket(rate = BigDecimal("0.066"), maxIncome = BigDecimal("52455")),
                    TaxBracket(rate = BigDecimal("0.088"), maxIncome = BigDecimal("66295")),
                    TaxBracket(rate = BigDecimal("0.1023"), maxIncome = BigDecimal("338639")),
                    TaxBracket(rate = BigDecimal("0.1133"), maxIncome = BigDecimal("406364")),
                    TaxBracket(rate = BigDecimal("0.1243"), maxIncome = BigDecimal("677275")),
                    TaxBracket(rate = BigDecimal("0.1353"), maxIncome = null)
                )
            ),
            deductions = listOf(
                Deduction(
                    name = "Standard federal deduction",
                    amount = BigDecimal("4803")
                )
            )
        )
    ),
    CaliforniaSdi(
        taxName = R.string.california_sdi,
        taxEntity = TaxEntity(
            name = "California SDI",
            jurisdiction = Jurisdiction.California,
            year = 2023,
            taxType = FixedWithMax(
                rate = BigDecimal("0.009"),
                maxTaxableIncome = BigDecimal("153164")
            ),
            deductions = listOf()
        )
    )
}
