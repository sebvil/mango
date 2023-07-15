package com.sebastianvm.mango.util.date

import kotlinx.datetime.LocalDate
import kotlinx.serialization.Serializable


/**
 * Inclusive date range
 */
@Serializable
data class DateRange(val startDate: LocalDate, val endDate: LocalDate)
