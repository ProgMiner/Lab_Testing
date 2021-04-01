package ru.byprogminer.lab1testing.task3


const val angryHumanWearing = "выцветшие синие балахоны и пояса Круксванского университета"

fun main() {
    val angryHuman1 = UnknownHuman()
    angryHuman1.wearing = angryHumanWearing

    val angryHuman2 = UnknownHuman()
    angryHuman2.wearing = angryHumanWearing

    val noise = Noise()
    val shout = Shout()

    noise.make(angryHuman1)
    shout.make(angryHuman2)

    val doors = Doors()
    val room = object: AbstractRoom() {

        override val doors = setOf(doors)
    }

    val lackey1 = Lackey()
    val lackey2 = Lackey()

    angryHuman1.enterRoom(room, doors)
    angryHuman2.enterRoom(room, doors)

    angryHuman1.pushUpPeople(setOf(lackey1))
    angryHuman2.pushUpPeople(setOf(lackey2))
}
