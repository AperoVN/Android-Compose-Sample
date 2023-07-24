package com.apero.sample.data.provider.media

import android.content.Context
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.TextUtils
import com.apero.sample.R
import com.apero.sample.data.model.AlbumMedia
import com.apero.sample.data.model.MediaModel
import java.io.File

/**
 * Created by KO Huyn on 04/07/2023.
 */
class MediaProviderImpl(private val context: Context) : MediaProvider {
    override suspend fun getPictureAlbums(): List<AlbumMedia> = queryAlbums()

    private suspend fun queryAlbums(): List<AlbumMedia> {
        val allPicture = queryImageFromStorage(context)
        val albums = allPicture.groupBy { it.bucketId to it.bucketName }
            .map { (key, listMedia) ->
                val (bucketId, bucketName) = key
                AlbumMedia(
                    id = bucketId,
                    name = bucketName,
                    listMedia = listMedia.sortedByDescending { it.id })
            }
        val all = AlbumMedia(
            id = context.getString(R.string.label_all),
            name = context.getString(R.string.label_all),
            listMedia = allPicture.sortedByDescending { it.id })

        val listAlbum = mutableListOf<AlbumMedia>()
        listAlbum.add(all)
        listAlbum.addAll(albums)
        return listAlbum
    }

    private suspend fun queryImageFromStorage(context: Context): List<MediaModel> {
        val allMedias = mutableListOf<MediaModel>()
        val projection = arrayOf(
            MediaStore.Images.Media.BUCKET_ID,
            MediaStore.Images.Media.BUCKET_DISPLAY_NAME,
            MediaStore.Images.Media._ID,
            MediaStore.Images.Media.DATA,
            MediaStore.Images.Media.WIDTH,
            MediaStore.Images.Media.HEIGHT,
            MediaStore.Images.Media.SIZE,
        )
        val collection: Uri = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            MediaStore.Images.Media.getContentUri(MediaStore.VOLUME_EXTERNAL)
        } else {
            MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        }
        try {
            context.contentResolver.query(collection, projection, null, null, null)
                .use { cursor ->
                    if (cursor != null) {
                        val bucketIdColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_ID)
                        val bucketNameColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.BUCKET_DISPLAY_NAME)
                        val idColumn = cursor.getColumnIndexOrThrow(MediaStore.Images.Media._ID)
                        val dataColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
                        val widthColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.WIDTH)
                        val heightColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.HEIGHT)
                        val sizeColumn =
                            cursor.getColumnIndexOrThrow(MediaStore.Images.Media.SIZE)
                        while (cursor.moveToNext()) {
                            val id = cursor.getLong(idColumn)
                            val path = cursor.getString(dataColumn)
                            val width = cursor.getLong(widthColumn)
                            val height = cursor.getLong(heightColumn)
                            val size = cursor.getLong(sizeColumn)
                            val bucketId = cursor.getString(bucketIdColumn)
                            var bucketName = cursor.getString(bucketNameColumn)
                            if (TextUtils.isEmpty(bucketName)) bucketName = "Phone storage"
                            if (!hasImage(path)) continue
                            if (isFileExisted(path)) {
                                val media = MediaModel(
                                    id = id,
                                    bucketId = bucketId,
                                    bucketName = bucketName,
                                    path = path
                                )
                                allMedias.add(media)
                            }
                        }
                    }
                }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return allMedias
    }

    private fun hasImage(path: String): Boolean {
        return path.endsWith(".jpg", true) || path.endsWith(".png", true)
    }

    private fun isFileExisted(filePath: String): Boolean {
        val file = File(filePath)
        return file.exists() && file.length() > 0
    }

}