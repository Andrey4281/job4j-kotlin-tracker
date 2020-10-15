package ru.job4j.tracker.service.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.job4j.tracker.repository.UserRepository
import java.nio.charset.StandardCharsets
import java.security.Key
import java.security.MessageDigest
import java.util.*

/**
 * Security service.
 * It is needed for checking passwords, creating and checking jwt tokens.
 * @author asemenov
 * @since 14.10.2020
 */
@Service("securityService")
class SecurityService(@Autowired private  val userRepository: UserRepository,
                      @Value("\${jwtTokenValiditySeconds}") private val jwtTokenValiditySeconds: String,
                      @Value("\${keyValue}") private val keyValue: String,
                      @Value("\${salt}") private val salt: String) {

    private val log = LogManager.getLogger(SecurityService::class.java)

    private val key: Key by lazy { initKey() }

    /**
     * check user password.
     */
    fun checkUser(login: String, password: String): Boolean {
        var res: Boolean
        val user = userRepository.findUserByLogin(login)

        val passwordHash = Base64.getEncoder().encodeToString(MessageDigest.getInstance("SHA-256")
                .digest(StringBuilder(password)
                        .append(salt).toString().toByteArray(StandardCharsets.UTF_8)))
        res = user?.password == passwordHash ?: false
        return res
    }

    /**
     * Generate jwt token.
     */
    fun generateToken(login: String): String {
        return Jwts.builder()
                .setSubject(login)
                .signWith(key, SignatureAlgorithm.HS512)
                .setExpiration(Date(System.currentTimeMillis()
                        + jwtTokenValiditySeconds.toInt() * 1000))
                .compact()
    }

    private fun initKey(): Key {
        return Keys.hmacShaKeyFor(Decoders.BASE64.decode(keyValue))
    }
}
