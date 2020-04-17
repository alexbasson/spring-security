package io.pivotal.resource.users

class InMemoryUserRepositoryTests : UserRepositoryTests() {
    override fun getUserRepository(): UserRepository = InMemoryUserRepository()
}
