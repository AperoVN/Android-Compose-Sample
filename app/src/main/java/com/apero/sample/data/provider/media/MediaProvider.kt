package com.apero.sample.data.provider.media

import android.Manifest
import android.annotation.SuppressLint
import androidx.annotation.RequiresPermission
import com.apero.sample.data.model.AlbumMedia

/**
 * @author KO Huyn
 * @since 04/07/2023
 */
interface MediaProvider {
    @SuppressLint("InlinedApi")
    @RequiresPermission(
        anyOf = [
            // API 32 or below
            Manifest.permission.MANAGE_EXTERNAL_STORAGE,
            // API 33 or above
            Manifest.permission.MANAGE_MEDIA,
            // API 32 or below
            Manifest.permission.READ_EXTERNAL_STORAGE,
            // API 33 or above
            Manifest.permission.READ_MEDIA_IMAGES,
        ],
    )
    suspend fun getPictureAlbums(): List<AlbumMedia>
}