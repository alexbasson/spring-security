package io.pivotal.resource.posts

import java.time.Instant

data class Post(
    val id: Long,
    val authorId: Long,
    val title: String,
    val body: String,
    val publishedDate: Instant? = null,
    val createdDate: Instant
)
