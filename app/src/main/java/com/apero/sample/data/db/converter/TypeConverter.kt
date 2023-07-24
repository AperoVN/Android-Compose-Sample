package com.apero.sample.data.db.converter

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Created by KO Huyn on 20/07/2023.
 */
class TypeConverter {
    @TypeConverter
    fun saveListInt(list: List<Int>): String {
        return Gson().toJson(list)
    }

    @TypeConverter
    fun getListInt(list: String): List<Int> {
        return Gson().fromJson(list, object : TypeToken<List<Int>>() {}.type)
    }
}