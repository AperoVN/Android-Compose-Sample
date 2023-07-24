package com.apero.sample.data.provider.imageurl

import com.apero.sample.config.Config
import com.apero.sample.data.model.ImageSize

/**
 * Created by KO Huyn on 21/07/2023.
 */
data class TmdbImagePathProvider(val imagePath: String?) {
    fun create(size: ImageSize): String? {
        if (imagePath.isNullOrBlank()) return null
        return Config.BASE_URL_IMAGE + size.size + imagePath
    }
}