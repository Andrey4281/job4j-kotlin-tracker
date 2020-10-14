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

    val create = fun(request: Request, response: Response): String {
        log.info("REST request to create item")
        val item = Gson().fromJson(request.body(), Item::class.java)
        response.status(200)
        return Gson().toJson(itemRepository.create(item), Item::class.java)
    }

    val update = fun(request: Request, response: Response): String {
        log.info("REST request to update item")
        val item = Gson().fromJson(request.body(), Item::class.java)
        log.info("Item.id={}", item.id)
        log.info("Item.name={}", item.name)
        response.status(200)
        itemRepository.update(item)
        return Gson().toJson(item, Item::class.java)
    }

    val delete = fun(request: Request, response: Response) {
        log.info("REST request to delete item {}", request.params(":id"))
        val item = itemRepository.findById(request.params(":id").toInt())
        if (item != null) {
            response.status(204)
            itemRepository.delete(request.params(":id").toInt())
        } else {
            response.status(404)
        }
    }

    val findAll = fun(_: Request, response: Response): String {
        log.info("REST request to findAll item")
        response.status(200)
        return Gson().toJson(itemRepository.findAll())
    }

    val findById = fun(request: Request, response: Response): String? {
        log.info("REST request to get by Id {}", request.params(":id").toInt())
        val item = itemRepository.findById(request.params(":id").toInt())
        if (item != null) {
            response.status(200)
            return Gson().toJson(itemRepository.findById(request.params(":id").toInt()))
        } else {
            response.status(404)
            return null
        }
    }
}
