package com.template.domain.entity.common

/**
 * Keep all the error related model class here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
sealed class ErrorEntity {

    data class Error(val errorCode: Any?, val errorMessage: String? = "") : ErrorEntity()
}
