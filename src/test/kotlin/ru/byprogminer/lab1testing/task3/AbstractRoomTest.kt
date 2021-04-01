package ru.byprogminer.lab1testing.task3

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class AbstractRoomTest {

    @MockK
    lateinit var human: Human

    @MockK
    lateinit var doors: Doors

    private lateinit var printStreamBuffer: ByteArrayOutputStream
    private lateinit var originalPrintStream: PrintStream

    @BeforeEach
    fun initMockK(): Unit = MockKAnnotations.init(this, relaxUnitFun = true)

    @BeforeEach
    fun initStdout() {
        originalPrintStream = System.out
        printStreamBuffer = ByteArrayOutputStream()
        System.setOut(PrintStream(printStreamBuffer))
    }

    @AfterEach
    fun finStdout(): Unit = System.setOut(originalPrintStream)

    @Test
    fun `Fresh AbstractRoom has no one people`() {
        val room = object: AbstractRoom() {

            override lateinit var doors: Set<Doors>
        }

        assertEquals(emptySet(), room.people, "fresh AbstractRoom has people")
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human can enter room`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns true
        every { human.toString() } returns "oaoaoa"

        room.enter(human, doors)

        assertEquals(setOf(human), room.people, "human not in room after entering")
        assertEquals("oaoaoa entered room.\n", printStreamBuffer.toString())
    }

    @Test
    fun `If door is not in room when entering throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = emptySet()
        }

        every { doors.isOpened } returns true

        val e = assertThrows<IllegalArgumentException> {
            room.enter(human, doors)
        }

        assertEquals("doors are not in this room", e.message)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `If doors are closed when entering throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns false

        val e = assertThrows<IllegalArgumentException> {
            room.enter(human, doors)
        }

        assertEquals("human cannot enter through closed doors", e.message)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `If human is already in room when entering throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns true
        every { human.toString() } returns "oaoaoa"

        room.enter(human, doors)

        val e = assertThrows<IllegalArgumentException> {
            room.enter(human, doors)
        }

        assertEquals("human is already in this room", e.message)
        assertEquals("oaoaoa entered room.\n", printStreamBuffer.toString())
    }

    @Test
    fun `Human can exit room`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns true
        every { human.toString() } returns "oaoaoa"

        room.enter(human, doors)
        room.exit(human, doors)

        assertEquals(setOf(), room.people, "human in room after exiting")
        assertEquals("oaoaoa entered room.\noaoaoa exited room.\n", printStreamBuffer.toString())
    }

    @Test
    fun `If door is not in room when exiting throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = emptySet()
        }

        every { doors.isOpened } returns true

        val e = assertThrows<IllegalArgumentException> {
            room.exit(human, doors)
        }

        assertEquals("doors are not in this room", e.message)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `If doors are closed when exiting throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns false

        val e = assertThrows<IllegalArgumentException> {
            room.exit(human, doors)
        }

        assertEquals("human cannot exit through closed doors", e.message)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `If human is not in room when exiting throws exception`() {
        val room = object: AbstractRoom() {

            override val doors: Set<Doors> = setOf(this@AbstractRoomTest.doors)
        }

        every { doors.isOpened } returns true

        val e = assertThrows<IllegalArgumentException> {
            room.exit(human, doors)
        }

        assertEquals("human is not in this room", e.message)
        assertEquals("", printStreamBuffer.toString())
    }
}
