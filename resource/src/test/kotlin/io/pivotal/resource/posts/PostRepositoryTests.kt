package io.pivotal.resource.posts

import io.pivotal.resource.posts.PostAssert.Companion.assertThat
import io.pivotal.resource.users.buildUser
import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.Assertions.assertThatThrownBy
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.time.Instant

abstract class PostRepositoryTests {
    abstract fun getPostRepository(): PostRepository

    private lateinit var postRepo: PostRepository
    private val author1 = buildUser()
    private val author2 = buildUser()

    @BeforeEach
    fun `set up`() {
        postRepo = getPostRepository()
    }

    @Test
    fun `create() creates posts with unique ids in the repo`() {
        val request1 = buildCreatePostRequest(authorId = author1.id)
        val post1_author1 = postRepo.create(request1)
        assertThat(post1_author1).matchesRequest(request1)

        val request2 = buildCreatePostRequest(authorId = author1.id)
        val post2_author1 = postRepo.create(request2)
        assertThat(post2_author1).matchesRequest(request2)

        val request3 = buildCreatePostRequest(authorId = author2.id)
        val post1_author2 = postRepo.create(request3)
        assertThat(post1_author2).matchesRequest(request3)

        val request4 = buildCreatePostRequest(authorId = author2.id)
        val post2_author2 = postRepo.create(request4)
        assertThat(post2_author2).matchesRequest(request4)

        assertThat(postRepo.findAll()).contains(post1_author1, post1_author2, post2_author1, post2_author2)
    }

    @Test
    fun `findAll() returns a set of all posts`() {
        val post1 = postRepo.create(buildCreatePostRequest(authorId = author1.id))
        val post2 = postRepo.create(buildCreatePostRequest(authorId = author2.id))
        val post3 = postRepo.create(buildCreatePostRequest(authorId = author1.id))

        assertThat(postRepo.findAll()).containsExactly(post1, post2, post3)
    }

    @Test
    fun `findById() finds posts by a given id`() {
        val post1 = postRepo.create(buildCreatePostRequest(authorId = author1.id))
        val post2 = postRepo.create(buildCreatePostRequest(authorId = author1.id))

        assertThat(postRepo.findById(post1.id)).isEqualTo(post1)
        assertThat(postRepo.findById(post2.id)).isEqualTo(post2)
    }

    @Test
    fun `findById() throws when given an id for which there is no post`() {
        assertThatThrownBy { postRepo.findById(123) }.isInstanceOf(PostNotFoundException::class.java)
    }

    @Test
    fun `findByAuthorId() returns the posts for a given author id`() {
        val post1_author1 = postRepo.create(buildCreatePostRequest(authorId = author1.id))
        val post1_author2 = postRepo.create(buildCreatePostRequest(authorId = author2.id))
        val post2_author1 = postRepo.create(buildCreatePostRequest(authorId = author1.id))
        val post2_author2 = postRepo.create(buildCreatePostRequest(authorId = author2.id))

        assertThat(postRepo.findByAuthorId(authorId = author1.id)).containsExactly(post1_author1, post2_author1)
        assertThat(postRepo.findByAuthorId(authorId = author2.id)).containsExactly(post1_author2, post2_author2)
    }

    @Test
    fun `findByAuthorId() returns an empty set when given an author id for which there are no posts`() {
        assertThat(postRepo.findByAuthorId(authorId = author1.id)).isEmpty()
    }

    @Test
    fun `update() updates the post with the given id`() {
        val post  = postRepo.create(buildCreatePostRequest(authorId = author1.id))
        assertThat(post.publishedDate).isNull()

        val publishedDate = Instant.now()
        val updatePostRequest = buildUpdatePostRequest(publishedDate = publishedDate)
        val updatedPost = postRepo.update(id = post.id, request = updatePostRequest)
        assertThat(updatedPost).matchesRequest(updatePostRequest)

        assertThat(updatedPost.id).isEqualTo(post.id)
        assertThat(updatedPost.title).isNotEqualTo(post.title)
        assertThat(updatedPost.body).isNotEqualTo(post.body)
        assertThat(updatedPost.publishedDate).isNotNull().isEqualTo(publishedDate)
    }

    @Test
    fun `update() throws when given an id for which there is no post`() {
        assertThatThrownBy { postRepo.update(123, buildUpdatePostRequest()) }.isInstanceOf(PostNotFoundException::class.java)
    }

    @Test
    fun `delete() deletes the post with the given id`() {
        val post = postRepo.create(buildCreatePostRequest(authorId = author1.id))

        assertThat(postRepo.findAll()).contains(post)
        assertThat(postRepo.findById(post.id)).isEqualTo(post)

        postRepo.delete(post.id)

        assertThat(postRepo.findAll()).isEmpty()
        assertThatThrownBy { postRepo.findById(post.id) }.isInstanceOf(PostNotFoundException::class.java)
    }

    @Test
    fun `delete() throws when given an id for which there is no post`() {
        assertThatThrownBy { postRepo.findById(123) }.isInstanceOf(PostNotFoundException::class.java)
    }
}
