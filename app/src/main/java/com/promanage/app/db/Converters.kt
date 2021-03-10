package com.promanage.app.db

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.JsonElement
import com.google.gson.reflect.TypeToken
import com.promanage.app.service.Labor
import com.promanage.app.service.Material
import java.util.*

class Converters {
    @TypeConverter
    fun fromTimestamp(value: Long?): Date? {
        return value?.let { Date(it) }
    }

    @TypeConverter
    fun dateToTimestamp(date: Date?): Long? {
        return date?.time?.toLong()
    }

    @TypeConverter
    fun gsonToMaterial(json: String?): List<Material> {
        return Gson().fromJson(json, object : TypeToken<List<Material>>() {}.type)
    }

    @TypeConverter
    fun materialToGson(materials: List<Material>): String? {
        return Gson().toJson(materials)
    }

    @TypeConverter
    fun gsonToLabor(json: String?): List<Labor> {
        return Gson().fromJson(json, object : TypeToken<List<Labor>>() {}.type)
    }

    @TypeConverter
    fun laborToGson(labors: List<Labor>): String? {
        return Gson().toJson(labors)
    }

    @TypeConverter
    fun stringToMap(json: String?): Map<String, Int> {
        return Gson().fromJson(json,  object : TypeToken<Map<String, Int>>() {}.type)
    }

    @TypeConverter
    fun mapToString(map: Map<String, Int>?): String {
        return if(map == null) "" else Gson().toJson(map)
    }
}