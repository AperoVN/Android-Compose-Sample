package com.apero.sample.data.model

import android.os.Parcelable

/**
 * Created by KO Huyn.
 */

@kotlinx.parcelize.Parcelize
data class AlbumMedia(val id: String, val name: String, val listMedia: List<MediaModel>):Parcelable {
    companion object {
        private val listAlbum = listOf("All", "Camera", "Screen Shot", "Picture", "Android  Art")
        fun mock(name: String = "Camera") =
            AlbumMedia(id = "", name = name, listMedia = List(10) { MediaModel.mock() })

        fun mockList() = listAlbum.map { mock(name = it) }
    }
}

@kotlinx.parcelize.Parcelize
data class MediaModel(
    val id: Long,
    val bucketId: String,
    val bucketName: String,
    val path: String
):Parcelable {
    companion object {
        fun mock() = MediaModel(bucketId = "", bucketName = "", path = "", id = 1)
    }
}