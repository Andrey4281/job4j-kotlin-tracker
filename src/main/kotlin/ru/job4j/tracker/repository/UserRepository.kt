package ru.job4j.tracker.repository

import org.apache.logging.log4j.LogManager
import org.hibernate.SessionFactory
import org.springframework.stereotype.Repository
import ru.job4j.tracker.config.hibernate.HibernateConfig
import ru.job4j.tracker.config.hibernate.tx
import ru.job4j.tracker.domain.User

@Repository
class UserRepository {
    private val log = LogManager.getLogger(UserRepository::class.java)

    private val sf: SessionFactory by lazy { init() }

    private fun init(): SessionFactory = HibernateConfig.sf

    fun create(user: User): User =
            sf.tx { save(user); user }

    fun findUserByLogin(name: String): User? {
        var res: User? = null
        sf.tx {
            val q = createQuery("from User where name = :name")
            q.setParameter("name", name)
            val users: List<User> = q.list() as List<User>
            if (users.isNotEmpty()) {
                res = users[0]
            }
        }
        return res
    }
}
