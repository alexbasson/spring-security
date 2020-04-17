package io.pivotal.identityserver

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableAuthorizationServer

@EnableAuthorizationServer
@SpringBootApplication
class IdentityServerApplication

fun main(args: Array<String>) {
	runApplication<IdentityServerApplication>(*args)
}
