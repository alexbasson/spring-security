package io.pivotal.resource.users

data class CreateUserRequest(
    val firstName: String,
    val lastName: String,
    val email: String
)

class UserNotFoundException : Throwable()

interface UserRepository {
    fun create(request: CreateUserRequest): User
    fun findAll(): Set<User>
    fun findById(id: Long): User
    fun delete(id: Long)
}
