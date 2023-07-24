package com.apero.sample.data.converter

interface IConverter<S, D> {
    fun convert(source: S): D
}
