package com.apero.sample.navigation.controller

import android.os.Parcelable
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavBackStackEntry
import com.apero.sample.data.serialize.deserialize
import com.apero.sample.data.serialize.serialize

fun NavBackStackEntry.stringArg(key: String): String = arg(key = key, type = string()) { it }
fun NavBackStackEntry.optionalStringArg(key: String): String? =
    optionalArg(key = key, type = string()) { it }

fun <ArgPrimitive, Arg> NavBackStackEntry.arg(
    key: String,
    type: NavBackStackEntry.(String) -> ArgPrimitive?,
    transform: (ArgPrimitive) -> Arg
): Arg =
    optionalArg(key = key, type = type, transform = transform) ?: error("missing '$key' argument")

fun <ArgPrimitive, Arg> NavBackStackEntry.optionalArg(
    key: String,
    type: NavBackStackEntry.(String) -> ArgPrimitive?,
    transform: (ArgPrimitive) -> Arg
): Arg? = type(key)?.let(transform)

fun string(): NavBackStackEntry.(String) -> String? = { key -> arguments?.getString(key) }
fun int(): NavBackStackEntry.(String) -> Int? = { key -> arguments?.getString(key)?.toIntOrNull() }
fun bool(): NavBackStackEntry.(String) -> Boolean? = { key -> arguments?.getBoolean(key) }

inline fun <reified T : Parcelable> SavedStateHandle.decode(key: String): T {
    return get<String>(key)?.let { deserialize(it) } ?: error("missing '$key' argument")
}

inline fun <Arg, reified T : Parcelable> NavigationController<Arg>.decode(encode: String): T {
    return deserialize(encode)
}

fun <Arg, D : Parcelable> NavigationController<Arg>.encode(data: D): String {
    return serialize(data)
}
