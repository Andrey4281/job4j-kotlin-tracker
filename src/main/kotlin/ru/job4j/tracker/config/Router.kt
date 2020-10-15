package ru.job4j.tracker.config
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import ru.job4j.tracker.config.filter.JsonFilter
import ru.job4j.tracker.controller.AuthController
import ru.job4j.tracker.controller.ItemController
import spark.Spark.*

@Component("router")
class Router(@Autowired private val itemController: ItemController,
             @Autowired private val jsonFilter: JsonFilter,
             @Autowired private val authController: AuthController) {
    init {
        path("/item") {
            before("/*", jsonFilter.handle)
            post("/create", itemController.create)
            put("/update", itemController.update)
            get("", itemController.findAll)
            get("/:id", itemController.findById)
        }
        path("/item") {
            delete("/:id", itemController.delete)
        }
        path("/auth") {
            before("/*", jsonFilter.handle)
            post("/signIn", authController.auth)
        }
    }
}
