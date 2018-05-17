package io.ride.vote.web.service.impl

import io.ride.vote.web.entity.User
import io.ride.vote.web.repository.UserRepository
import io.ride.vote.web.service.IUserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class UserService : IUserService {

    @Autowired
    private lateinit var userRepository: UserRepository

    override fun listAll(): List<User> {
        return userRepository.findAll()
    }

    override fun addUser(user: User): User {
        return userRepository.save(user)
    }

    override fun deleteUser(id: Long) {
        userRepository.deleteById(id)
    }
}