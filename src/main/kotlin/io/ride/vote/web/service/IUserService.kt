package io.ride.vote.web.service

import io.ride.vote.web.entity.User


interface IUserService {
    /**
     * 添加一个用户
     */
    fun addUser(user: User): User

    /**
     * 展示所有用户
     */
    fun listAll(): List<User>

    /**
     * 删除一个用户
     */
    fun deleteUser(id: Long)

}