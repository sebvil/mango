package com.sebastianvm.mango.database.converters

import androidx.room.TypeConverter
import com.sebastianvm.mango.model.Deduction
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class DeductionConverters {
    @TypeConverter
    fun fromString(value: String): List<Deduction> {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun taxTypeToString(value: List<Deduction>): String {
        return Json.encodeToString(value)
    }
}
