package com.apero.sample.data.model

sealed class ImageSize(val size: String) {

    sealed class BackdropSize(size: String) : ImageSize(size) {
        object W300 : BackdropSize("w300")
        object W780 : BackdropSize("w780")
        object W1280 : BackdropSize("w1280")
        object ORIGINAL : BackdropSize("original")
    }

    sealed class LogoSize(size: String) : ImageSize(size) {
        object W45 : LogoSize("w45")
        object W92 : LogoSize("w92")
        object W154 : LogoSize("w154")
        object W185 : LogoSize("w185")
        object W300 : LogoSize("w300")
        object W500 : LogoSize("w500")
        object ORIGINAL : LogoSize("original")
    }

    sealed class PosterSize(size: String) : ImageSize(size) {
        object W92 : PosterSize("w92")
        object W154 : PosterSize("w154")
        object W185 : PosterSize("w185")
        object W342 : PosterSize("w342")
        object W500 : PosterSize("w500")
        object W780 : PosterSize("w780")
        object ORIGINAL : PosterSize("original")
    }
}