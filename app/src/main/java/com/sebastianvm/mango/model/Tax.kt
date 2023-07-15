package com.sebastianvm.mango.model

interface Tax {
    val id: Long
    val name: String
    val jurisdiction: Jurisdiction
    val taxType: TaxType
    val deductions: List<Deduction>
}