package io.ride.vote.web.repository

import io.ride.vote.web.entity.User
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface UserRepository : JpaRepository<User, Long> {

    override fun findAll(): List<User>

    override fun findById(id: Long): Optional<User>

    fun save(user: User): User

    override fun deleteById(id: Long)

}