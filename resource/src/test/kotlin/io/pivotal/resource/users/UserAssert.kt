package io.pivotal.resource.users

import org.assertj.core.api.AbstractAssert

class UserAssert(actual: User) : AbstractAssert<UserAssert, User>(actual, UserAssert::class.java) {
    companion object {
        fun assertThat(actual: User) = UserAssert(actual)
    }

    fun matchesRequest(request: CreateUserRequest): UserAssert {
        val firstNameMatches = actual.firstName == request.firstName
        val lastNameMatches = actual.lastName == request.lastName
        val emailMatches = actual.email == request.email

        if (!(firstNameMatches && lastNameMatches && emailMatches)) {
            failWithMessage("Expected user to match createUserRequest")
        }
        return this
    }
}
