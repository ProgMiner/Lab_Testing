package ru.byprogminer.lab1testing.task3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream

class IntegrationTest {

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
    fun `Main works properly`() {
        main()

        assertEquals("""
Unknown human made noise.
Unknown human made shout.
Unknown human opened doors.
Unknown human entered room.
Unknown human entered room.
Unknown human pushed up Lackey.
Unknown human pushed up Lackey.

        """.trimIndent(), printStreamBuffer.toString())
    }
}
