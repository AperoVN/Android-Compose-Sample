package com.apero.sample.data.provider.media

import com.apero.sample.data.model.AlbumMedia

/**
 * Created by KO Huyn on 04/07/2023.
 */
interface MediaProvider {
    suspend fun getPictureAlbums(): List<AlbumMedia>
}