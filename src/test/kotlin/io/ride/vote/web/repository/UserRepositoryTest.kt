package io.ride.vote.web.repository

import io.ride.vote.web.entity.User
import org.junit.Test
import org.junit.runner.RunWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.junit4.SpringRunner

@RunWith(SpringRunner::class)
@SpringBootTest
class UserRepositoryTest {

    @Autowired
    private lateinit var userRepository: UserRepository

    @Test
    fun `find all user test`() {
        println(userRepository.findAll())
    }

    @Test
    fun `add user test`() {
        val user = User(username = "ride", email = "supreDong@gmail.com", password = "123123", nickname = "ride")
        println(userRepository.save(user))
    }

    @Test
    fun `delete user test`() {
        val user = userRepository.findById(1)
        println(user.orElse(null))
        if (user.isPresent)
            userRepository.deleteById(user.get().id)
    }
}