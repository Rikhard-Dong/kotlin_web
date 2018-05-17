package io.ride.vote.web.dto

import io.swagger.annotations.ApiModelProperty
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity

data class Result(
        @ApiModelProperty("状态码", required = true)
        val code: Int,
        @ApiModelProperty("消息", required = true)
        val msg: String = "",
        @ApiModelProperty("数据", required = true)
        var data: HashMap<String, Any>? = null) {

    constructor(result: ResultCode) : this(result.code, result.msg)
    constructor(result: ResultCode, data: HashMap<String, Any>?) : this(result.code, result.msg, data)

    fun addData(key: String, value: Any): Result {
        if (data == null) {
            data = HashMap()
        }
        data!![key] = value
        return this
    }

    fun ok(): ResponseEntity<Result> {
        return ResponseEntity(this, HttpStatus.OK)
    }
}

