package ru.job4j.tracker.controller
import com.google.gson.Gson
import org.apache.logging.log4j.LogManager
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Controller
import ru.job4j.tracker.domain.Item
import ru.job4j.tracker.repository.ItemRepository
import spark.Request
import spark.Response

@Controller
class ItemController(@Autowired private val itemRepository: ItemRepository) {
    private val log = LogManager.getLogger(ItemController::class.java)

    fun create(request: Request, response: Response): String {
        log.info("info")
        log.error("error")
        log.debug("debug")
        val item = Gson().fromJson(request.body(), Item::class.java)
        response.status(200)
        return Gson().toJson(itemRepository.create(item), Item::class.java)
    }

    fun update(request: Request, response: Response): String {
        val item = Gson().fromJson(request.body(), Item::class.java)
        response.status(200)
        itemRepository.update(item)
        return Gson().toJson(item, Item::class.java)
    }

    fun delete(request: Request, response: Response) {
        val item = itemRepository.findById(request.params(":id").toInt())
        if (item != null) {
            response.status(204)
            itemRepository.delete(request.params(":id").toInt())
        } else {
            response.status(404)
        }
    }

    fun findAll(request: Request, response: Response): String {
        response.status(200)
        return Gson().toJson(itemRepository.findAll())
    }

    fun findById(request: Request, response: Response): String? {
        val item = itemRepository.findById(request.params(":id").toInt())
        return if (item != null) {
            response.status(200)
            Gson().toJson(itemRepository.findById(request.params(":id").toInt()))
        } else {
            response.status(404)
            null
        }
    }
}
