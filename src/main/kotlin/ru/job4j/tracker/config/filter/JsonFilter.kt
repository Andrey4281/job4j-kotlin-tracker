package ru.job4j.tracker.config.filter

import org.apache.logging.log4j.LogManager
import org.springframework.stereotype.Component
import spark.Request
import spark.Response

@Component
class JsonFilter {
    private val log = LogManager.getLogger(JsonFilter::class.java)

    fun handle(request: Request, response: Response) {
        log.info("JSON FILTER")
        response.type("application/json")
    }
}
