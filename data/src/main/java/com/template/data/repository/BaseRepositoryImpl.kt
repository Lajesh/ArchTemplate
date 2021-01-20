@file:Suppress("UNCHECKED_CAST")
package com.template.data.repository

import android.util.MalformedJsonException
import com.google.gson.GsonBuilder
import com.template.data.constants.NetworkConstants
import com.template.data.datasource.remote.dto.ErrorDto
import com.template.domain.common.ResultState
import com.template.domain.entity.common.ErrorEntity
import okhttp3.ResponseBody
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.io.InterruptedIOException
import java.net.SocketException
import java.net.SocketTimeoutException

/**
 * Base Repository implementation class
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
abstract class BaseRepositoryImpl {

    private val logFormatter: String = "%s | %s"

    /**
     * This method returns the proper error codes and error messages for various
     * network exceptions and API related errors
     */
    internal fun handleError(throwable: Throwable): ResultState.Error<ResultState<ErrorEntity.Error>> {
        return when (throwable) {

            is SocketTimeoutException, is SocketException, is InterruptedIOException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE
                )
                ResultState.Error(
                    ErrorEntity.Error(
                        NetworkConstants.NETWORK_ERROR_CODES.SERVICE_UNAVAILABLE,
                        NetworkConstants.NETWORK_ERROR_MESSAGES.SERVICE_UNAVAILABLE
                    )
                )
            }

            is MalformedJsonException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.MALFORMED_JSON
                )
                ResultState.Error(
                    ErrorEntity.Error(
                        NetworkConstants.NETWORK_ERROR_CODES.MALFORMED_JSON,
                        NetworkConstants.NETWORK_ERROR_MESSAGES.MALFORMED_JSON
                    )
                )
            }
            is IOException -> {
                Timber.e(
                    logFormatter,
                    throwable.message.toString(),
                    NetworkConstants.NETWORK_ERROR_MESSAGES.NO_INTERNET
                )
                ResultState.Error(
                    ErrorEntity.Error(
                        NetworkConstants.NETWORK_ERROR_CODES.NO_INTERNET,
                        NetworkConstants.NETWORK_ERROR_MESSAGES.NO_INTERNET
                    )
                )
            }

            is HttpException -> {

                Timber.e(
                    logFormatter,
                    throwable.response()?.toString() ?: throwable.message().toString(),
                    ""
                )
                val errorResponse: ErrorDto.ErrorResponse? =
                    getError(throwable.response()?.errorBody())
                if (errorResponse?.error == null) {
                    ResultState.Error(
                        ErrorEntity.Error(
                            NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                            NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                        )
                    )
                } else {

                    if (errorResponse.error.code == "ValidationError" && errorResponse.error.fieldErrors?.isNotEmpty() == true) {
                        errorResponse.error.errorUserMsg =
                            errorResponse.error.fieldErrors[0].message
                    }
                    Timber.e(
                        logFormatter,
                        errorResponse.error.code,
                        errorResponse.error.errorUserMsg.toString()
                    )
                    ResultState.Error(
                        ErrorEntity.Error(
                            errorResponse.error.code,
                            errorResponse.error.errorUserMsg.toString()
                        )
                    )
                }
            }
            else -> {
                Timber.e(
                    logFormatter, NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                )
                ResultState.Error(
                    ErrorEntity.Error(
                        NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                        NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR
                    )
                )
            }
        }
    }

    /**
     * This method serializes the errorbody to ErrorDto class
     */
    private fun getError(responseBody: ResponseBody?): ErrorDto.ErrorResponse? {
        return try {
            val response = GsonBuilder().create()
                .fromJson(responseBody?.string(), ErrorDto.ErrorResponse::class.java)
            Timber.e("API Error Object: %s", response?.toString())
            response
        } catch (ex: Exception) {
            ErrorDto.ErrorResponse(
                ErrorDto.Error(
                    "", NetworkConstants.NETWORK_ERROR_CODES.UNEXPECTED_ERROR,
                    NetworkConstants.NETWORK_ERROR_MESSAGES.UNEXPECTED_ERROR, null
                )
            )
        }
    }
}
