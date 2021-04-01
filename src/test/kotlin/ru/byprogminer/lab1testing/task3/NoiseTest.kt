package ru.byprogminer.lab1testing.task3

import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import java.io.ByteArrayOutputStream
import java.io.PrintStream
import kotlin.test.assertEquals

class NoiseTest {

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
    fun `Can make noise`() {
        val noise = Noise()

        noise.make("dude")

        assertEquals("dude made noise.\n", printStreamBuffer.toString())
    }
}
