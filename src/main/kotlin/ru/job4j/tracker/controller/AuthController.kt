package ru.job4j.tracker.controller

import com.google.gson.Gson
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import ru.job4j.tracker.service.security.SecurityService
import ru.job4j.tracker.service.security.dto.UserDTO
import spark.Request
import spark.Response

@Controller
class AuthController(@Autowired private val securityService: SecurityService) {
    private val log = LogManager.getLogger(AuthController::class.java)

    fun auth(request: Request, response: Response): String? {
        val user = Gson().fromJson(request.body(), UserDTO::class.java)
        val res = securityService.checkUser(user.login, user.password)
        return if (res) {
            response.status(200)
            "{\"token\": ${securityService.generateToken(user.login)}}"
        } else {
            response.status(401)
            "{\"message\": invalid user}"
        }
    }
}
