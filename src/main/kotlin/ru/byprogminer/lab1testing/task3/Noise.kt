package ru.byprogminer.lab1testing.task3

class Noise: Sound {

    override fun make(initiator: Any) {
        println("$initiator made noise.")
    }
}
