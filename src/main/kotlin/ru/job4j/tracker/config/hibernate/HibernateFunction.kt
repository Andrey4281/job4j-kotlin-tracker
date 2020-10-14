package ru.job4j.tracker.config.hibernate

import org.hibernate.Session
import org.hibernate.SessionFactory

fun <T> SessionFactory.tx(block: Session.() -> T): T {
    val session = openSession()
    session.beginTransaction()
    val model = session.block()
    session.transaction.commit()
    session.close()
    return model
}

infix fun <T> SessionFactory.select(query: String): List<T> {
    val session = openSession()
    session.beginTransaction()
    val model = session.createQuery(query).list() as List<T>
    session.transaction.commit()
    session.close()
    return model
}
