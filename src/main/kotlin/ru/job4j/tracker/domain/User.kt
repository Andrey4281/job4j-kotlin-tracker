package ru.job4j.tracker.domain

import javax.persistence.*

@Entity
@Table(name = "users")
data class User(@Id
                @GeneratedValue(strategy = GenerationType.IDENTITY) var id: Int = 0,
                @Column(name = "name") var name: String = "",
                @Column(name = "password") var password: String = "")
