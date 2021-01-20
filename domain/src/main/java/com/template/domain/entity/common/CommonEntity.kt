package com.template.domain.entity.common

/**
 * Keep all the common entity class here
 * Created by Lajesh Dineshkumar on 2020-03-09.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
sealed class CommonEntity {

    data class CommonResponse<T>(
        val response: Any?,
        val data: T?
    ) : CommonEntity()

    data class ServerDate(
        val dateTime: String? = ""
    ) : CommonEntity()
}
