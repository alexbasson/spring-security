package io.pivotal.resource.posts

import java.time.Instant

class InMemoryPostRepository : PostRepository {
    private val posts = mutableSetOf<Post>()

    override fun create(request: CreatePostRequest): Post {
        val id = posts.map { it.id }.max()?.plus(1) ?: 1
        posts.add(Post(
            id = id,
            authorId = request.authorId,
            title = request.title,
            body = request.body,
            createdDate = Instant.now()
        ))
        return findById(id)
    }

    override fun findAll(): Set<Post> {
        return posts
    }

    override fun findById(id: Long): Post {
        return posts.find { it.id == id } ?: throw PostNotFoundException()
    }

    override fun findByAuthorId(authorId: Long): Set<Post> {
        return posts.filter { it.authorId == authorId }.toSet()
    }

    override fun update(id: Long, request: UpdatePostRequest): Post {
        val post = findById(id)
        val updatedPost = post.copy(
            title = request.title ?: post.title,
            body = request.body ?: post.body,
            publishedDate = request.publishedDate ?: post.publishedDate
        )
        posts.remove(post)
        posts.add(updatedPost)
        return findById(id)
    }

    override fun delete(id: Long) {
        val post = findById(id)
        posts.remove(post)
    }
}
