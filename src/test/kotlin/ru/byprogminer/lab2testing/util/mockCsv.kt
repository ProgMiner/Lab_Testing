package ru.byprogminer.lab2testing.util

import io.mockk.every
import ru.byprogminer.lab2testing.math.BasedMathFunction
import ru.byprogminer.lab2testing.math.MathFunction
import java.util.*
import kotlin.test.currentStackTrace


@Suppress("NOTHING_TO_INLINE")
private inline fun getPreviousClass(): Class<*> =
    Class.forName(currentStackTrace()[1].className)

@Suppress("NOTHING_TO_INLINE")
private inline fun readCsvResource(filename: String): Iterable<List<String>> =
    readCsv(Scanner(getPreviousClass().getResourceAsStream(filename)))

fun MathFunction.mockCsv(filename: String) = readCsvResource(filename)
    .map { it.map(String::toDouble) }.forEach { line ->
        if (line.size < 2) {
            throw IllegalArgumentException("Invalid amount of columns in: $line")
        }

        every {
            hint(Double::class) // https://github.com/mockk/mockk/issues/492

            this@mockCsv(line[0])
        } returns line[1]
    }

fun BasedMathFunction.mockCsv(filename: String) = readCsvResource(filename)
    .map { it.map(String::toDouble) }.forEach { line ->
        if (line.size < 3) {
            throw IllegalArgumentException("Invalid amount of columns in: $line")
        }

        every {
            hint(Double::class) // https://github.com/mockk/mockk/issues/492

            this@mockCsv(line[0], match { it.toDouble() == line[1] })
        } returns line[2]
    }
