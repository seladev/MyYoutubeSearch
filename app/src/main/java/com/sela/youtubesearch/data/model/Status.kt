package com.sela.youtubesearch.data.model

/**
 * Request Status - for displaying ui when the request flow
 */

class Status(val status: StatusType, val message: String = "") {}

enum class StatusType {
    Error,
    Loading,
    Success
}
