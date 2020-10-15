package ru.job4j.tracker

import org.springframework.context.annotation.AnnotationConfigApplicationContext



fun main() {
    val ctx = AnnotationConfigApplicationContext(TrackerConfig::class.java)
    ctx.getBean("router")
}
