package com.apero.sample.utils

import androidx.compose.ui.Modifier

/**
 * Created by KO Huyn on 24/07/2023.
 */

fun Modifier.ifOrIgnore(condition: Boolean, block: Modifier.() -> Modifier): Modifier {
    return this.run {
        if (condition) {
            this.block()
        } else this
    }
}
