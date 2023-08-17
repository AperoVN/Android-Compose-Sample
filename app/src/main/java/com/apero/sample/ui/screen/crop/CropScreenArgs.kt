package com.apero.sample.ui.screen.crop

import android.os.Bundle
import android.os.Parcelable
import androidx.core.os.BundleCompat
import androidx.lifecycle.SavedStateHandle
import androidx.navigation.NavArgs
import androidx.navigation.NavType
import kotlinx.parcelize.Parcelize
import kotlinx.serialization.Serializable
import kotlinx.serialization.json.Json

@Serializable
@Parcelize
data class CropScreenArgs(
    val imageUri: String,
) : NavArgs, Parcelable {

    companion object Type : NavType<CropScreenArgs>(isNullableAllowed = false) {
        override fun get(bundle: Bundle, key: String): CropScreenArgs? {
            return BundleCompat.getParcelable(bundle, key, CropScreenArgs::class.java)
        }

        override fun parseValue(value: String): CropScreenArgs {
            return Json.decodeFromString(value)
        }

        override fun put(bundle: Bundle, key: String, value: CropScreenArgs) {
            bundle.putParcelable(key, value)
        }
    }
}
