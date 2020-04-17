package io.pivotal.resource.posts

import org.assertj.core.api.AbstractAssert

class PostAssert(actual: Post) : AbstractAssert<PostAssert, Post>(actual, PostAssert::class.java) {
    companion object {
        fun assertThat(actual: Post) = PostAssert(actual)
    }

    fun matchesRequest(request: CreatePostRequest): PostAssert {
        val authorIdMatches = actual.authorId == request.authorId
        val titleMatches = actual.title == request.title
        val bodyMatches = actual.body == request.body

        if (!(authorIdMatches && titleMatches && bodyMatches)) {
            failWithMessage("Expected post to match createPostRequest")
        }
        return this
    }

    fun matchesRequest(request: UpdatePostRequest): PostAssert {
        val titleMatches = actual.title == request.title
        val bodyMatches = actual.body == request.body
        val publishedDateMatches = actual.publishedDate == request.publishedDate

        if (!(titleMatches && bodyMatches && publishedDateMatches)) {
            failWithMessage("Expected updated post to match updatePostRequest")
        }
        return this
    }
}
