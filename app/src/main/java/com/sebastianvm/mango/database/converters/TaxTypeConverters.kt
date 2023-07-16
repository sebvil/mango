package com.sebastianvm.mango.database.converters

import androidx.room.TypeConverter
import com.sebastianvm.mango.model.TaxType
import kotlinx.serialization.encodeToString
import kotlinx.serialization.json.Json

class TaxTypeConverters {
    @TypeConverter
    fun fromString(value: String): TaxType {
        return Json.decodeFromString(value)
    }

    @TypeConverter
    fun taxTypeToString(value: TaxType): String {
        return Json.encodeToString(value)
    }
}
