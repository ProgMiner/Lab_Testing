package ru.byprogminer.lab1testing.task3

interface Room {

    val people: Set<Human>
    val doors: Set<Doors>

    fun enter(human: Human, doors: Doors)
    fun exit(human: Human, doors: Doors)
}
