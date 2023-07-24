package com.apero.sample.data.state

import android.content.Context
import androidx.annotation.StringRes

sealed class UiText {

    abstract fun getBy(context: Context): String

    class Dynamic(private val value: String) : UiText() {
        override fun getBy(context: Context): String = value
    }

    class StringResource(
        @StringRes private val resId: Int,
        private vararg val args: Any,
    ) : UiText() {
        override fun getBy(context: Context): String {
            return context.getString(resId, *args)
        }
    }

    class NestedStringResource(
        @StringRes private val resId: Int,
        private vararg val args: UiText,
    ) : UiText() {
        override fun getBy(context: Context): String {
            return context.getString(resId, *args.map { it.getBy(context) }.toTypedArray())
        }
    }

    companion object {
        fun from(value: String) = Dynamic(value)
        fun from(@StringRes resId: Int) = StringResource(resId)
        fun from(@StringRes resId: Int, vararg args: Any) = StringResource(resId, *args)
        fun from(@StringRes resId: Int, vararg args: UiText) = NestedStringResource(resId, *args)
    }

}