package io.ride.vote.web.dto

enum class ResultCode(val code: Int, val msg: String = "") {
    OK(200),
    ERROR_NOT_FOUNT(400, "Not found")
}