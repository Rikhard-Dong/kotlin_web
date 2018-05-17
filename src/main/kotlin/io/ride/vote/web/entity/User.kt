package io.ride.vote.web.entity

import java.util.*
import javax.persistence.*

@Entity
@Table(name = "t_user")
data class User(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long = -1,
        @Column(nullable = false)
        var username: String? = null,
        @Column(nullable = false)
        var password: String? = null,
        @Column(nullable = false)
        var email: String? = null,
        @Column(nullable = true)
        var nickname: String? = null,
        @Column(nullable = false)
        var createTime: Date = Date()
)