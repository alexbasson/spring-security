package io.pivotal.identityserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class IdentityServerApplication

fun main(args: Array<String>) {
	runApplication<IdentityServerApplication>(*args)
}
