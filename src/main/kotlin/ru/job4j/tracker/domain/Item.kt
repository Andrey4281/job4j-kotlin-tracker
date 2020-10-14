package ru.job4j.tracker.domain

import javax.persistence.*

@Entity
@Table(name = "items")
data class Item(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Int = 0,
        var name: String = "")
