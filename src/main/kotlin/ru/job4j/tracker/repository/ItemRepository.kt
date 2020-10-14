package ru.job4j.tracker.repository

import org.apache.logging.log4j.LogManager
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import ru.job4j.tracker.config.hibernate.HibernateConfig
import ru.job4j.tracker.config.hibernate.select
import ru.job4j.tracker.config.hibernate.tx
import ru.job4j.tracker.domain.Item

@Repository
class ItemRepository {
    private val log = LogManager.getLogger(ItemRepository::class.java)

    private val sf: SessionFactory by lazy { init()}

    private fun init(): SessionFactory = HibernateConfig.sf

    fun create(item: Item): Item =
            sf.tx { save(item); item }

    fun update(item: Item?) {
        sf.tx { update(item) }
    }

    fun delete(id: Int) {
        sf.tx {
            val q = createQuery("delete Item where id = :id").setParameter("id", id)
            q.executeUpdate()
        }
    }

    fun findAll(): List<Item> = sf select "from Item"

    fun findById(id: Int?): Item? =
            sf.tx { get(Item::class.java, id) }
}
