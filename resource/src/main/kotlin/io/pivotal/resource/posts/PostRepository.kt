package io.pivotal.resource.posts

import java.time.Instant

data class CreatePostRequest(
    val authorId: Long,
    val title: String,
    val body: String
)

data class UpdatePostRequest(
    val title: String?,
    val body: String?,
    val publishedDate: Instant?
)

class PostNotFoundException: Throwable()

interface PostRepository {
    fun create(request: CreatePostRequest): Post
    fun findAll(): Set<Post>
    fun findById(id: Long): Post
    fun findByAuthorId(authorId: Long): Set<Post>
    fun update(id: Long, request: UpdatePostRequest): Post
    fun delete(id: Long)
}
