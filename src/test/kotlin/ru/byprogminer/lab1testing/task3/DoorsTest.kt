package ru.byprogminer.lab1testing.task3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class DoorsTest {

    private lateinit var printStreamBuffer: ByteArrayOutputStream
    private lateinit var originalPrintStream: PrintStream

    @BeforeEach
    fun initStdout() {
        originalPrintStream = System.out
        printStreamBuffer = ByteArrayOutputStream()
        System.setOut(PrintStream(printStreamBuffer))
    }

    @AfterEach
    fun finStdout(): Unit = System.setOut(originalPrintStream)

    @Test
    fun `Fresh doors are closed`() {
        val doors = Doors()

        assertEquals(false, doors.isOpened, "fresh doors are opened")
        assertEquals("", printStreamBuffer.toString())
    }

    @Test
    fun `Can open doors`() {
        val doors = Doors()

        doors.open("dude")

        assertEquals(true, doors.isOpened, "doors aren't opened")
        assertEquals("dude opened doors.\n", printStreamBuffer.toString())
    }

    @Test
    fun `Cannot open opened doors`() {
        val doors = Doors()

        doors.open("dude")

        val e = assertThrows<IllegalStateException> {
            doors.open("dude")
        }

        assertEquals(e.message, "doors are already opened")
        assertEquals("dude opened doors.\n", printStreamBuffer.toString())
    }

    @Test
    fun `Can close doors`() {
        val doors = Doors()

        doors.open("dude")
        doors.close("dude")

        assertEquals(false, doors.isOpened, "doors are opened")
        assertEquals("dude opened doors.\ndude closed doors.\n", printStreamBuffer.toString())
    }

    @Test
    fun `Cannot close closed doors`() {
        val doors = Doors()

        val e = assertThrows<IllegalStateException> {
            doors.close("dude")
        }

        assertEquals(e.message, "doors are already closed")
        assertEquals("", printStreamBuffer.toString())
    }
}
