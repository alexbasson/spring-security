package io.pivotal.resource.posts

class InMemoryPostRepositoryTests : PostRepositoryTests() {
    override fun getPostRepository() = InMemoryPostRepository()
}
