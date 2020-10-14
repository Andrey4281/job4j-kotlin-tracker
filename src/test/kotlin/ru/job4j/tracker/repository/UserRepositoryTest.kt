package ru.job4j.tracker.repository

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import ru.job4j.tracker.domain.User

internal class UserRepositoryTest: StringSpec({

    val userRepository = UserRepository()

    "When find user by login then should get it" {
        var userActual = User(name = "user", password = "password")
        userActual = userRepository.create(userActual)
        userActual shouldBe userRepository.findUserByLogin("user")
    }
})
