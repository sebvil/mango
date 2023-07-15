package com.sebastianvm.mango.model


/**
 * Some income might not be paid directly to the person, but still be counted for tax purposed.
 * An example is life insurance. The property [paidToSelf] is included to track this.
 */
interface IncomeStream {
    val id: Long
    val name: String
    val type: IncomeInfo
    val paidToSelf: Boolean
}