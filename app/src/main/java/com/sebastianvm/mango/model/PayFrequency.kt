package com.sebastianvm.mango.model

import com.sebastianvm.mango.util.date.DateRange
import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable

/**
 *
 * Pay period: A pay period is a time frame used to calculate earned wages and determine when
 * employees receive their paychecks. Pay periods are fixed and most often recurring on a weekly,
 * bi-weekly, semi-monthly or monthly basis.
 *
 * Pay Date: The employee does not receive his or her check at the end of the pay period, but a
 * number of days after it has concluded, which is known as the pay date.
 *
 *  [Source](https://www.netsuite.com/portal/resource/articles/human-resources/pay-period.shtml)
 *
 *  For these, we include pay period and pay day information:
 *  @property firstPayPeriod Defines the range of the first pay period during which a person is
 *  paid. This can then be used to calculate all the following pay periods.
 *
 *
 *  @property daysFromEndOfPayPeriodToPayDay Used to determine when a person is actually paid for
 *  pay period.
 *
 *
 *  @property lastDay The last day a person earns the income. Note this is not the last day the
 *  person will get paid. That day will be the last day of the current pay period + [daysFromEndOfPayPeriodToPayDay]
 *
 */
@Serializable
sealed interface PayFrequency {
    val firstPayPeriod: DateRange
    val daysFromEndOfPayPeriodToPayDay: Int
    val lastDay: LocalDate?


    /**
     * For one time payments
     */
    @Serializable
    data class OneTime(val date: LocalDate) : PayFrequency {
        override val firstPayPeriod: DateRange = DateRange(date, date)
        override val daysFromEndOfPayPeriodToPayDay: Int = 0
        override val lastDay: LocalDate = date
    }

    /**
     * For payments every week
     */
    @Serializable
    data class Weekly(
        override val firstPayPeriod: DateRange,
        override val daysFromEndOfPayPeriodToPayDay: Int,
        override val lastDay: LocalDate? = null
    ) : PayFrequency


    /**
     * For payments every two weeks
     */
    @Serializable
    data class SemiWeekly(
        override val firstPayPeriod: DateRange,
        override val daysFromEndOfPayPeriodToPayDay: Int,
        override val lastDay: LocalDate? = null
    ) : PayFrequency


    /**
     * For payments twice a month (15 and last day)
     */
    @Serializable
    data class SemiMonthly(
        override val firstPayPeriod: DateRange,
        override val daysFromEndOfPayPeriodToPayDay: Int,
        override val lastDay: LocalDate? = null
    ) : PayFrequency


    /**
     * For monthly payments
     */
    @Serializable
    data class Monthly(
        override val firstPayPeriod: DateRange,
        override val daysFromEndOfPayPeriodToPayDay: Int,
        override val lastDay: LocalDate? = null
    ) : PayFrequency

}