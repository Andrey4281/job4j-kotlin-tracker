package ru.job4j.tracker.config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.job4j.tracker.config.filter.JsonFilter
import ru.job4j.tracker.config.filter.JwtFilter
import ru.job4j.tracker.config.handler.ExceptionsHandler
import ru.job4j.tracker.controller.AuthController
import ru.job4j.tracker.controller.ItemController
import ru.job4j.tracker.service.security.exception.InvalidJwtException
import spark.Spark.*

@Component("router")
class Router(@Autowired private val itemController: ItemController,
             @Autowired private val jsonFilter: JsonFilter,
             @Autowired private val authController: AuthController,
             @Autowired private val jwtFilter: JwtFilter,
             @Autowired private val exceptionsHandler: ExceptionsHandler) {
    init {
        path("/item") {
            before("/*", jsonFilter::handle)
            before("/*", jwtFilter::handle)
            exception(InvalidJwtException::class.java, exceptionsHandler::handleInvalidToken)
            post("/create", itemController::create)
            put("/update", itemController::update)
            get("/findAll", itemController::findAll)
            get("/:id", itemController::findById)
        }
        path("/item") {
            before("/*", jwtFilter::handle)
            delete("/:id", itemController::delete)
        }
        path("/auth") {
            before("/*", jsonFilter::handle)
            post("/signIn", authController::auth)
        }
    }
}
