package io.ride.vote.web.exception

import io.ride.vote.web.dto.Result
import io.ride.vote.web.dto.ResultCode
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.MissingServletRequestParameterException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler

@ControllerAdvice
class CustomExceptionHandler {

    @ExceptionHandler(ApiException::class)
    fun handlerApiException(e: ApiException): ResponseEntity<Result> {
        val result = Result(e.code, e.data)
        return result.ok()

    }

    @ExceptionHandler(MissingServletRequestParameterException::class)
    fun handMissingServletRequestParameterException(e: MissingServletRequestParameterException): ResponseEntity<Result> {

        val result = Result(HttpStatus.BAD_REQUEST.value(), e.message)
        return result.ok()
    }

}

class ApiException(val code: ResultCode, val data: HashMap<String, Any>? = null) : RuntimeException(code.msg)