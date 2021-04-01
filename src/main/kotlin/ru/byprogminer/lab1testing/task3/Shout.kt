package ru.byprogminer.lab1testing.task3

class Shout: Sound {

    override fun make(initiator: Any) {
        println("$initiator made shout.")
    }
}
