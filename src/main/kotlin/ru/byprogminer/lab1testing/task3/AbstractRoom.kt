package ru.byprogminer.lab1testing.task3

abstract class AbstractRoom: Room {

    private val _people = mutableSetOf<Human>()
    override val people: Set<Human> get() = _people

    override fun enter(human: Human, doors: Doors) {
        if (doors !in this.doors) {
            throw IllegalArgumentException("doors are not in this room")
        }

        if (!doors.isOpened) {
            throw IllegalArgumentException("human cannot enter through closed doors")
        }

        if (human in people) {
            throw IllegalArgumentException("human is already in this room")
        }

        _people.add(human)
        println("$human entered room.")
    }

    override fun exit(human: Human, doors: Doors) {
        if (doors !in this.doors) {
            throw IllegalArgumentException("doors are not in this room")
        }

        if (!doors.isOpened) {
            throw IllegalArgumentException("human cannot exit through closed doors")
        }

        if (human !in people) {
            throw IllegalArgumentException("human is not in this room")
        }

        _people.remove(human)
        println("$human exited room.")
    }
}
