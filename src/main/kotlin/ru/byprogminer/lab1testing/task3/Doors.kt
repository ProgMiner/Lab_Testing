package ru.byprogminer.lab1testing.task3

class Doors {

    var isOpened = false
        private set

    fun open(initiator: Any) {
        if (isOpened) {
            throw IllegalStateException("doors are already opened")
        }

        isOpened = true
        println("$initiator opened doors.")
    }

    fun close(initiator: Any) {
        if (!isOpened) {
            throw IllegalStateException("doors are already closed")
        }

        isOpened = false
        println("$initiator closed doors.")
    }
}
