package io.pivotal.resource.users

import java.util.*

fun buildUser(
    id: Long = Random().nextLong(),
    firstName: String = "first_${id}",
    lastName: String = "last_${id}",
    email: String = "${firstName}.${lastName}@email.com"
) = User(
    id = id,
    firstName = firstName,
    lastName = lastName,
    email = email
)

fun buildCreateUserRequest(
    firstName: String = "first_${Random().nextInt()}",
    lastName: String = "last_${Random().nextInt()}",
    email: String = "${firstName}.${lastName}@email.com"
) = CreateUserRequest(
    firstName = firstName,
    lastName = lastName,
    email = email
)
