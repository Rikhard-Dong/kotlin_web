package io.ride.vote.web.controller

import io.ride.vote.web.dto.Result
import io.ride.vote.web.dto.ResultCode
import io.ride.vote.web.entity.User
import io.ride.vote.web.service.impl.UserService
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.annotations.ApiParam
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

@Api("用户操作", description = "对用户进行增删改查")
@RestController
@RequestMapping("/user")
class UserController {
    @Autowired
    private lateinit var userService: UserService


    @GetMapping("/all")
    @ApiOperation("获取所有的用户", notes = "获取所有用户")
    fun getAllUser(): ResponseEntity<Result> {
        val users = userService.listAll()
        val result = Result(ResultCode.OK)
        return result.addData("users", users).ok()
    }

    @PostMapping("/add")
    @ApiOperation("添加一个用户", notes = "")
    fun addUser(user: User): ResponseEntity<Result> {
        userService.addUser(user)
        val result = Result(ResultCode.OK)
        return result.ok()
    }

    @DeleteMapping("/delete/{id}")
    @ApiOperation("删除一个用户", notes = "")
    fun deleteUser(@ApiParam(required = true, value = "用户id") @PathVariable("id", required = true) id: Long): ResponseEntity<Result> {
        userService.deleteUser(id)
        val result = Result(ResultCode.OK)
        return result.ok()
    }
}