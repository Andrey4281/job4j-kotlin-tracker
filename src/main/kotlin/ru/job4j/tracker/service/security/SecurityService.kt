package ru.job4j.tracker.service.security

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.io.Decoders
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import ru.job4j.tracker.repository.UserRepository
import java.security.Key
import java.util.*

/**
 * Security service.
 * It is needed for checking passwords, creating and checking jwt tokens.
 * @author asemenov
 * @since 14.10.2020
 */
@Service("securityService")
class SecurityService(@Autowired private  val userRepository: UserRepository) {

    @Value("jwtTokenValiditySeconds")
    private lateinit var jwtTokenValiditySeconds: String

    @Value("keyValue")
    private lateinit var keyValue: String


    private val key: Key by lazy { initKey() }

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
