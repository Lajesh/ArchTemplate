package com.template.cleanapp.utils

import okhttp3.mockwebserver.MockResponse
import okio.buffer
import okio.source
import java.io.InputStream

/**
 * Mock Response builder
 * Created by Lajesh Dineshkumar on 10/31/2019.
 * Company: Nagarro Middle East
 * Email: lajesh.dineshkumar@nagarro.com
 */
object MockResponse {

    /**
     * Method which will create the mock response for the API call from the json stored in resources
     * @param fileName : Response json file name
     * @param responseCode : HTTP status code
     * @return mock json response
     */
    fun createMockResponse(fileName: String? = null, responseCode: Int): MockResponse {
        val mockResponse = MockResponse()
        mockResponse.setResponseCode(responseCode)
        if (!fileName.isNullOrEmpty())
            mockResponse.setBody(
                getDataFromJson(fileName)
            )
        return mockResponse
    }

    /**
     * Returns the json data
     * @param fileName : Filename of json response
     */
    private fun getDataFromJson(fileName: String): String {
        val source = createInputStream(fileName)!!
            .source()
            .buffer()
        return source.readString(Charsets.UTF_8)
    }

    /**
     * Creates input stream for the specified file
     */
    private fun createInputStream(fileName: String): InputStream? {
        return javaClass.classLoader?.getResourceAsStream("mock_response/$fileName")
    }
}
