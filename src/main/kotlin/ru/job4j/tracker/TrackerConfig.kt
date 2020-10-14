package ru.job4j.tracker

import org.springframework.context.annotation.ComponentScan
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.PropertySource

@Configuration
@ComponentScan(basePackages = ["ru.job4j.tracker"])
@PropertySource("security.properties")
open class TrackerConfig
