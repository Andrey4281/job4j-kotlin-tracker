package ru.job4j.tracker.repository

import io.kotlintest.shouldBe
import io.kotlintest.specs.StringSpec
import ru.job4j.tracker.domain.Item

internal class ItemRepositoryTest: StringSpec({

    val itemRepository = ItemRepository()

    "When create item then should get it" {
        val itemActual = Item(name = "test")
        val itemExpected = itemRepository.create(itemActual)
        itemActual shouldBe itemRepository.findById(itemExpected.id)
    }

    "When update item then should get updated value" {
        var itemActual = Item(name = "test1")
        itemActual = itemRepository.create(itemActual)
        itemActual.name = "Test test test"
        itemRepository.update(itemActual)
        "Test test test" shouldBe itemRepository.findById(itemActual.id)!!.name
    }

    "When delete item then should not get it" {
        var itemActual = Item(name = "test2")
        itemActual = itemRepository.create(itemActual)
        itemRepository.delete(itemActual.id)
        itemRepository.findById(itemActual.id) shouldBe null
    }
})
