package com.apero.sample.data.state

import com.apero.sample.data.network.exception.FailureException
import retrofit2.Response
import kotlin.jvm.Throws


/**
 * Created by KO Huyn.
 */
sealed interface ResultState<T> {
    data class Success<T>(val data: T) : ResultState<T>
    data class Failure<T>(val failureState: FailureState) : ResultState<T> {
        fun isNoContent() = failureState is FailureState.NoContent
    }

    fun <R> map(transform: (T) -> R): ResultState<R> {
        return when (this) {
            is Success -> {
                Success(transform(data))
            }
            is Failure -> Failure(failureState)
        }
    }

    @Throws(FailureException::class)
    fun getOrThrow(): T {
        return when (this) {
            is Success -> data
            is Failure -> throw FailureException(failureState)
        }
    }

    fun getOrNull(): T? {
        return when (this) {
            is Success -> data
            is Failure -> null
        }
    }

    fun getOrElse(onFailure: (failureState: FailureState) -> T): T {
        return when (this) {
            is Success -> data
            is Failure -> onFailure(failureState)
        }
    }

    fun onSuccess(success: (data: T) -> Unit): ResultState<T> {
        getOrNull()?.let(success)
        return this
    }

    fun onEach(block: (result: ResultState<T>) -> Unit): ResultState<T> {
        block(this)
        return this
    }


    fun onSuccessNonData(success: (data: T?) -> Unit): ResultState<T> {
        when (this) {
            is Success -> success(data)
            is Failure -> {
                if (isNoContent()) {
                    success(null)
                }
            }
        }
        return this
    }

    fun onError(error: (failureState: FailureState) -> Unit): ResultState<T> {
        if (this is Failure) {
            error(failureState)
        }
        return this
    }

    fun either(
        success: (data: T) -> Unit,
        failure: (failureState: FailureState) -> Unit
    ): ResultState<T> {
        return this.onSuccess(success).onError(failure)
    }

    companion object {
        suspend inline fun <T> fromApiResponse(
            crossinline f: suspend () -> Response<T>,
        ): ResultState<T> {
            return try {
                val response = f()
                if (response.isSuccessful) {
                    val responseBody = response.body()
                    if (responseBody != null) {
                        Success(responseBody)
                    } else {
                        Failure(FailureState.NoContent)
                    }
                } else {
                    Failure(FailureState.ServerError(response))
                }
            } catch (ex: Exception) {
                Failure(FailureState.Exception(ex))
            }
        }
    }
}