package io.pivotal.resource.users

class InMemoryUserRepository : UserRepository {
    private val users = mutableSetOf<User>()

    override fun create(request: CreateUserRequest): User {
        val id = users.map { it.id }.max()?.plus(1) ?: 1
        users.add(User(
            id = id,
            firstName = request.firstName,
            lastName = request.lastName,
            email = request.email
        ))
        return findById(id)
    }

    override fun findAll(): Set<User> {
        return users
    }

    override fun findById(id: Long): User {
        return users.find { it.id == id } ?: throw UserNotFoundException()
    }

    override fun delete(id: Long) {
        val user = findById(id)
        users.remove(user)
    }
}
