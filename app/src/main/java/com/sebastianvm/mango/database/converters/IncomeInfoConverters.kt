package com.sebastianvm.mango.database.converters

import androidx.room.TypeConverter
import com.sebastianvm.mango.model.IncomeInfo
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class IncomeInfoConverters {
    @TypeConverter
    fun fromString(value: String): IncomeInfo {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun incomeInfoToString(value: IncomeInfo): String {
        return Json.encodeToString(value)
    }

}