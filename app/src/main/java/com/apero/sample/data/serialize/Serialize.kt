package com.apero.sample.data.serialize

import android.os.Parcelable
import com.google.gson.Gson

/**
 * Created by KO Huyn.
 */

fun <D : Parcelable> serialize(data: D): String {
    return Gson().toJson(data)
}

inline fun <reified D : Parcelable> deserialize(encode: String): D {
    return Gson().fromJson(encode, D::class.java)
}