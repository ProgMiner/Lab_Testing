package ru.byprogminer.lab1testing.task3

abstract class AbstractHuman(val name: String): Human {

    override lateinit var wearing: String
    override lateinit var mood: String

    override fun enterRoom(room: Room, doors: Doors) {
        if (doors !in room.doors) {
            throw IllegalArgumentException("doors aren't in room")
        }

        if (!doors.isOpened) {
            doors.open(this)
        }

        room.enter(this, doors)
    }

    override fun exitRoom(room: Room, doors: Doors) {
        if (doors !in room.doors) {
            throw IllegalArgumentException("doors aren't in room")
        }

        if (!doors.isOpened) {
            doors.open(this)
        }

        room.exit(this, doors)
    }

    override fun pushUpPeople(people: Set<Human>) {
        if (people.isEmpty()) {
            throw IllegalArgumentException("people cannot be empty")
        }

        if (this in people) {
            throw IllegalArgumentException("human cannot push up himself")
        }

        println("$this pushed up ${people.joinToString(", ")}.")
    }

    override fun toString(): String {
        return name
    }
}
