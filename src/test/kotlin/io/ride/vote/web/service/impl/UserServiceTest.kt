package io.ride.vote.web.service.impl


import io.ride.vote.web.entity.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired

import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserServiceTest {

    @Autowired
    private lateinit var userService: UserService

    @Test
    fun `list all user`() {
        println(userService.listAll())
    }

    @Test
    fun `add user`() {
        val user = User(username = "salvation", nickname = "salvation", email = "salvation@gmail.com", password = "123123")
        userService.addUser(user)
    }

    @Test
    fun `delete user`() {
        var user = User(username = "test", nickname = "test", email = "test@test.com", password = "123123")
        user = userService.addUser(user)
        println(user)
        userService.deleteUser(user.id)
    }

}
