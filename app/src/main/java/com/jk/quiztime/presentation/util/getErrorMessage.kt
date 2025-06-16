package com.jk.quiztime.presentation.util

import com.jk.quiztime.domain.util.DataError

fun DataError.getErrorMessage() : String {
    return when(this) {
        DataError.NoInternet -> "No internet connection. Check your network"
        DataError.RequestTimeout -> ""
        DataError.Serialization -> "Failed to process data. Try again"
        DataError.Server -> "Server error"
        DataError.TooManyRequests -> "Too many requests"
        is DataError.Unknown -> "An unknown error occurred. Topic name not found"

        else -> ""
    }
}