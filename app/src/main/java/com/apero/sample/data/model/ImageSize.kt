package com.apero.sample.data.model

sealed class ImageSize(val size: String) {
    sealed class BackdropSize(size: String) : ImageSize(size) {
        object W300 : BackdropSize("w300")
        object W780 : BackdropSize("w780")
        object W1280 : BackdropSize("w1280")
        object ORIGINAL : BackdropSize("original")
        companion object {
            fun getBySize(width: Int): ImageSize {
                return when (width) {
                    in 0..300 -> W300
                    in 301..780 -> W780
                    in 781..1280 -> W1280
                    else -> ORIGINAL
                }
            }
        }

    }

    sealed class LogoSize(size: String) : ImageSize(size) {
        object W45 : LogoSize("w45")
        object W92 : LogoSize("w92")
        object W154 : LogoSize("w154")
        object W185 : LogoSize("w185")
        object W300 : LogoSize("w300")
        object W500 : LogoSize("w500")
        object ORIGINAL : LogoSize("original")
        companion object {
            fun getBySize(width: Int): ImageSize {
                return when (width) {
                    in 0..45 -> W45
                    in 46..92 -> W92
                    in 93..154 -> W154
                    in 155..185 -> W185
                    in 186..300 -> W300
                    in 301..500 -> W500
                    else -> ORIGINAL
                }
            }
        }
    }

    sealed class PosterSize(size: String) : ImageSize(size) {
        object W92 : PosterSize("w92")
        object W154 : PosterSize("w154")
        object W185 : PosterSize("w185")
        object W342 : PosterSize("w342")
        object W500 : PosterSize("w500")
        object W780 : PosterSize("w780")
        object ORIGINAL : PosterSize("original")

        companion object {
            fun getBySize(width: Int): ImageSize {
                return when (width) {
                    in 0..92 -> W92
                    in 93..154 -> W154
                    in 155..185 -> W185
                    in 185..342 -> W342
                    in 343..500 -> W500
                    in 501..780 -> W780
                    else -> ORIGINAL
                }
            }
        }
    }
}