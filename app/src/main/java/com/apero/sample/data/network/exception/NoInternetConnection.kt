package com.apero.sample.data.network.exception

import java.io.IOException

/**
 * Created by KO Huyn on 10/07/2023.
 */
class NoInternetConnection(override val message: String? = "No Internet Connection") : IOException()