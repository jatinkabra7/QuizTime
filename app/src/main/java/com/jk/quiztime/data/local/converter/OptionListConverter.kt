package com.jk.quiztime.data.local.converter

import androidx.room.TypeConverter
import kotlinx.serialization.json.Json
import kotlinx.serialization.encodeToString

class OptionListConverter {

    @TypeConverter
    fun fromListToString(list : List<String>) : String {
        return Json.encodeToString(list)
    }

    @TypeConverter
    fun fromStringToList(listString: String) : List<String> {
        return Json.decodeFromString(listString)
    }
}