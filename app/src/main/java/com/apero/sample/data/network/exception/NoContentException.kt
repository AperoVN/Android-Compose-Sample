package com.apero.sample.data.network.exception

class NoContentException constructor(
    override val message: String? = "The server has successfully fulfilled the request and that there is no additional content to send in the response payload body.",
) : Throwable(message)