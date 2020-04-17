package io.pivotal.resource.users

import io.pivotal.resource.users.UserAssert.Companion.assertThat
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

abstract class UserRepositoryTests {
    abstract fun getUserRepository(): UserRepository

    private lateinit var userRepo: UserRepository

    @BeforeEach
    fun `set up`() {
        userRepo = getUserRepository();
    }

    @Test
    fun `create() creates users with unique ids in the repo`() {
        val request1 = buildCreateUserRequest()
        val user1 = userRepo.create(request1)

        val request2 = buildCreateUserRequest()
        val user2 = userRepo.create(request2)

        assertThat(user1).matchesRequest(request1)
        assertThat(user2).matchesRequest(request2)
        assertThat(user1.id).isNotEqualTo(user2.id)
    }

    @Test
    fun `findAll() returns a set of all users in the repo`() {
        val user1 = userRepo.create(buildCreateUserRequest())
        val user2 = userRepo.create(buildCreateUserRequest())

        assertThat(userRepo.findAll()).containsExactly(user1, user2)
    }

    @Test
    fun `findById() finds users by id`() {
        val user1 = userRepo.create(buildCreateUserRequest())
        val user2 = userRepo.create(buildCreateUserRequest())

        assertThat(userRepo.findById(id = user1.id)).isEqualTo(user1)
        assertThat(userRepo.findById(id = user2.id)).isEqualTo(user2)
    }

    @Test
    fun `findById() throws when given a non-existent id`() {
        assertThatThrownBy { userRepo.findById(id = 123) }.isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun `delete() removes the user with the given id from the repo`() {
        val user = userRepo.create(buildCreateUserRequest())

        assertThat(userRepo.findAll()).contains(user)
        assertThat(userRepo.findById(id = user.id)).isEqualTo(user)

        userRepo.delete(id = user.id)

        assertThat(userRepo.findAll()).isEmpty()
        assertThatThrownBy { userRepo.findById(id = user.id) }.isInstanceOf(UserNotFoundException::class.java)
    }

    @Test
    fun `delete() throws when given an id that doesn't exist`() {
        assertThatThrownBy { userRepo.delete(id = 123) }.isInstanceOf(UserNotFoundException::class.java)
    }

}
