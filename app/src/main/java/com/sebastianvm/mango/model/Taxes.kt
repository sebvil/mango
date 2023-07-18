package com.sebastianvm.mango.model

import androidx.annotation.RawRes
import com.sebastianvm.mango.R

enum class Taxes(@RawRes val location: Int) {
    FederalIncome(R.raw.federal_tax_2023),
    Medicare(R.raw.medicare_2023),
    SocialSecurity(R.raw.social_security_2023)
}
