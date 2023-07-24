package com.apero.sample.data.state

import android.util.Log
import androidx.annotation.DrawableRes
import com.apero.sample.R
import com.apero.sample.data.network.exception.NoInternetConnection
import retrofit2.Response

/**
 * Created by KO Huyn.
 */
sealed class FailureState {
    abstract fun toUiState(): FailureUi
    data class ServerError<T>(val response: Response<T>) : FailureState() {
        override fun toUiState(): FailureUi {
            return FailureUi(
                imageRes = null,
                label = UiText.from(response.message()),
                describe = UiText.from(response.errorBody()?.string() ?: ""),
                buttonText = null
            )
        }

        override fun toString(): String {
            return response.errorBody()?.string() ?: response.message()
        }
    }

    data class Exception(val exception: Throwable) : FailureState() {
        override fun toUiState(): FailureUi {
            return when (exception) {
                is NoInternetConnection -> {
                    FailureUi(
                        imageRes = null,
                        label = UiText.from(R.string.label_no_internet),
                        describe = UiText.from(R.string.content_no_internet),
                        buttonText = UiText.from(R.string.retry),
                        buttonVisible = true
                    )
                }

                else -> {
                    FailureUi(
                        imageRes = null,
                        label = UiText.from(R.string.error_unknown),
                        describe = UiText.from(exception.toString()),
                        buttonText = null
                    ).also { Log.e("Exception", exception.message ?: "", exception) }
                }
            }
        }

        override fun toString(): String {
            return exception.message ?: exception.toString()
        }
    }

    object NoInternet : FailureState() {
        override fun toUiState(): FailureUi {
            return FailureUi(
                imageRes = null,
                label = UiText.from(R.string.label_no_internet),
                describe = UiText.from(R.string.content_no_internet),
                buttonText = UiText.from(R.string.retry),
                buttonVisible = true
            )
        }
    }

    object NoContent : FailureState() {
        override fun toUiState(): FailureUi {
            return FailureUi(null, null, null, null)
        }
    }
}

data class FailureUi(
    @DrawableRes val imageRes: Int?,
    val label: UiText?,
    val describe: UiText?,
    val buttonText: UiText?,
    val buttonVisible: Boolean = false
) {
    companion object {
        fun mock(): FailureUi {
            return FailureUi(
                imageRes = null,
                label = UiText.from(R.string.label_no_internet),
                describe = UiText.from(R.string.content_no_internet),
                buttonText = UiText.from(R.string.retry),
                buttonVisible = true
            )
        }
    }
}