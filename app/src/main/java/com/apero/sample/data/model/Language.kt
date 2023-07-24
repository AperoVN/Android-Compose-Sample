package com.apero.sample.data.model

import androidx.annotation.DrawableRes
import com.apero.sample.R
import com.apero.sample.data.state.UiText

enum class Language(
    val countryCode: String,
    val countryName: UiText,
    @DrawableRes val flag: Int
) {
    EN("en", UiText.from("English"), R.drawable.ic_language_en),
    RU("ru", UiText.from("РУССКИЙ"), R.drawable.ic_language_ru),
    VI("vi", UiText.from("Tiếng Việt"), R.drawable.ic_language_vi),
    FR("fr", UiText.from("Française"), R.drawable.ic_language_fr),
    DE("de", UiText.from("Deutsch"), R.drawable.ic_language_de),
    HI("hi", UiText.from("हिंदी"), R.drawable.ic_language_hi),
    ES("es", UiText.from("Español"), R.drawable.ic_language_es),
    PT("pt", UiText.from("Português"), R.drawable.ic_language_pt);

    companion object {
        fun findFromCountryCode(countryCode: String): Language? {
            return values().find { it.countryCode == countryCode }
        }

        fun getDefault() = EN
    }
}

