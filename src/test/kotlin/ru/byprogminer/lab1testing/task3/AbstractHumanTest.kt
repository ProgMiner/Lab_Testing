package ru.byprogminer.lab1testing.task3

import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.verify
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class AbstractHumanTest {

    @MockK
    lateinit var room: Room

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
    fun `Human can enter room`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf(doors)
        every { doors.isOpened } returns true

        human.enterRoom(room, doors)

        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 1) { room.enter(human, doors) }
    }

    @Test
    fun `If door is not in room when entering throws exception`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf()

        val e = assertThrows<IllegalArgumentException> {
            human.enterRoom(room, doors)
        }

        assertEquals("doors aren't in room", e.message)
        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 0) { doors.isOpened }
        verify(exactly = 0) { room.enter(human, doors) }
    }

    @Test
    fun `If doors are closed when entering opening them`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf(doors)
        every { doors.isOpened } returns false

        human.enterRoom(room, doors)

        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 1) { doors.open(human) }
        verify(exactly = 1) { room.enter(human, doors) }
    }

    @Test
    fun `Human can exit room`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf(doors)
        every { doors.isOpened } returns true

        human.exitRoom(room, doors)

        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 1) { room.exit(human, doors) }
    }

    @Test
    fun `If door is not in room when exiting throws exception`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf()

        val e = assertThrows<IllegalArgumentException> {
            human.exitRoom(room, doors)
        }

        assertEquals("doors aren't in room", e.message)
        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 0) { doors.isOpened }
        verify(exactly = 0) { room.exit(human, doors) }
    }

    @Test
    fun `If doors are closed when exiting opening them`() {
        val human = object: AbstractHuman("test") {}

        every { room.doors } returns setOf(doors)
        every { doors.isOpened } returns false

        human.exitRoom(room, doors)

        assertEquals("", printStreamBuffer.toString())

        verify(exactly = 1) { doors.open(human) }
        verify(exactly = 1) { room.exit(human, doors) }
    }

    @Test
    fun `Human can wear something`() {
        val human = object: AbstractHuman("test") {}

        human.wearing = "test"

        assertEquals("test", human.wearing)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human wear nothing at start`() {
        val human = object: AbstractHuman("test") {}

        assertThrows<UninitializedPropertyAccessException> {
            human.wearing
        }

        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human can have mood`() {
        val human = object: AbstractHuman("test") {}

        human.mood = "test"

        assertEquals("test", human.mood)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human hasn't any mood at start`() {
        val human = object: AbstractHuman("test") {}

        assertThrows<UninitializedPropertyAccessException> {
            human.mood
        }

        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human can push up people`() {
        val human = object: AbstractHuman("test") {}

        val people = setOf(
            object: AbstractHuman("oaoaoa") {}
        )

        human.pushUpPeople(people)

        assertEquals("test pushed up oaoaoa.\n", printStreamBuffer.toString())
    }

    @Test
    fun `Human cannot push up no one`() {
        val human = object: AbstractHuman("test") {}

        val e = assertThrows<IllegalArgumentException> {
            human.pushUpPeople(emptySet())
        }

        assertEquals("people cannot be empty", e.message)
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Human cannot push up himself`() {
        val human = object: AbstractHuman("test") {}

        val e = assertThrows<IllegalArgumentException> {
            human.pushUpPeople(setOf(human))
        }

        assertEquals("human cannot push up himself", e.message)
        assertEquals("", printStreamBuffer.toString())
    }
}
