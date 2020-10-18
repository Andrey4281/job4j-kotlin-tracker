package ru.job4j.tracker.config.filter

import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.job4j.tracker.service.security.SecurityService
import ru.job4j.tracker.service.security.exception.InvalidJwtException
import spark.Request
import spark.Response

@Component
class JwtFilter(@Autowired private val securityService: SecurityService) {
    private val log = LogManager.getLogger(JwtFilter::class.java)

    fun handle(request: Request, response: Response) {
        log.info("JWT filter")
        val header = request.headers("Authorization")
        if (header != null && header.startsWith("Bearer ")) {
            val authToken = header.replace("Bearer ", "")
            securityService.validateToken(authToken)
        } else {
            log.error("Invalid authorization header format")
            throw InvalidJwtException("Invalid authorization header format")
        }
    }
}
