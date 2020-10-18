package ru.job4j.tracker.config.handler

import com.google.gson.Gson
import org.springframework.stereotype.Component
import ru.job4j.tracker.service.security.dto.ErrorDTO
import spark.Request
import spark.Response

@Component
class ExceptionsHandler {
     fun handleInvalidToken(exception: Throwable, request: Request, response: Response) {
        val error = ErrorDTO(401, exception.message)

        response.status(401)
        response.body(Gson().toJson(error))
    }
}
