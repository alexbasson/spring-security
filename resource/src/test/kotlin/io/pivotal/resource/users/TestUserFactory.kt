package io.pivotal.resource.users

import java.util.*

fun buildCreateUserRequest(
    firstName: String = "first_${Random().nextInt()}",
    lastName: String = "last_${Random().nextInt()}",
    email: String = "${firstName}.${lastName}@email.com"
) = CreateUserRequest(
    firstName = firstName,
    lastName = lastName,
    email = email
)
