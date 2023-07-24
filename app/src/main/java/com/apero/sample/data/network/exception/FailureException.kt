package com.apero.sample.data.network.exception

import com.apero.sample.data.state.FailureState
import java.io.IOException

/**
 * Created by KO Huyn on 17/07/2023.
 */
class FailureException(val failureState: FailureState) : IOException(failureState.toString())