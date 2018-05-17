package io.ride.vote.web.controller

import io.ride.vote.web.dto.Result
import io.ride.vote.web.dto.ResultCode
import io.ride.vote.web.exception.ApiException
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api(value = "测试", description = "测试控制器")
@RestController
@RequestMapping("/hello")
class HelloController {

    @GetMapping
    @ApiOperation("你好!世界!", notes = "返回hello world")
    fun hello(): String {
        return "hello world"
    }

    /* @GetMapping("/")
     @ApiOperation(value = "测试", notes = "你好, 这是一个测试接口")
     fun hello(): ResponseEntity<Result> {
         return Result(200, "hello").addData("key", "value").ok()
     }*/

    @PostMapping("/name")
    @ApiOperation("测试post接口", notes = "POST测试接口")
    fun hello(@RequestParam("name", required = true) name: String): ResponseEntity<Result> {
        val resultDto = Result(200, "0k")
        resultDto.addData("name", name)
        return resultDto.ok()
    }

    @GetMapping("/exception/{status}")
    @ApiOperation(value = "异常测试", notes = "自定义异常测试, 当status等于error时抛出异常")
    fun exception(@PathVariable("status") status: String): ResponseEntity<Result> {
        if (status == "error") {
            throw ApiException(ResultCode.ERROR_NOT_FOUNT)
        }
        val result = Result(ResultCode.OK)
        return result.addData("status", status).ok()
    }
}