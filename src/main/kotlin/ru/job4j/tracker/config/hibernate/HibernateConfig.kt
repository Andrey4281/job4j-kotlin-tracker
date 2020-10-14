package ru.job4j.tracker.config.hibernate

import org.hibernate.SessionFactory
import org.hibernate.boot.MetadataSources
import org.hibernate.boot.registry.StandardServiceRegistryBuilder

object HibernateConfig {
    val sf: SessionFactory by lazy { init() }

    private fun init(): SessionFactory {
        val registry = StandardServiceRegistryBuilder().configure().build()
        return MetadataSources(registry).buildMetadata().buildSessionFactory()
    }
}
