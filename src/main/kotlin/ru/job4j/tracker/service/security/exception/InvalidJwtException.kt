package ru.job4j.tracker.service.security.exception

class InvalidJwtException(private val msg: String): Exception(msg)
