package io.pivotal.resource.posts

import java.time.Instant
import java.util.*

fun buildCreatePostRequest(
    authorId: Long,
    title: String = "A random title ${Random().nextInt()}",
    body: String = "A random body ${Random().nextInt()}"
) = CreatePostRequest(
    authorId = authorId,
    title = title,
    body = body
)

fun buildUpdatePostRequest(
    title: String? = "An updated title ${Random().nextInt()}",
    body: String? = "An updated body ${Random().nextInt()}",
    publishedDate: Instant = Instant.now()
) = UpdatePostRequest(
    title = title,
    body = body,
    publishedDate = publishedDate
)
