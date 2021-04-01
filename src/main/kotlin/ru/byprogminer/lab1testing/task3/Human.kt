package ru.byprogminer.lab1testing.task3

interface Human {

    val wearing: String
    val mood: String

    fun enterRoom(room: Room, doors: Doors)
    fun exitRoom(room: Room, doors: Doors)

    fun pushUpPeople(people: Set<Human>)
}
