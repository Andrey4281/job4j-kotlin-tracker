package ru.job4j.tracker.config.filter

import org.springframework.stereotype.Component
import spark.Request
import spark.Response

@Component
class JsonFilter {
    val handle = fun (res: Request, response: Response) {
        response.type("application/json")
    }
}
