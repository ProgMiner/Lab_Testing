package ru.byprogminer.lab2testing

import ru.byprogminer.lab2testing.math.*
import ru.byprogminer.lab2testing.util.writeCsv
import java.io.IOException
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.StandardOpenOption
import kotlin.system.exitProcess


private data class Args(
    val filename: String,
    val range: DoubleIterator,
    val precision: Double,
    val function: String,
)

private fun parseArgs(args: Array<String>): Args? {
    val begin = args[1].toDoubleOrNull() ?: return null
    val next = args[2].toDoubleOrNull() ?: return null
    val end = args[3].toDoubleOrNull() ?: return null
    val precision = (args.getOrNull(4) ?: "1e-4").toDoubleOrNull() ?: return null
    val function = args.getOrNull(5) ?: "task"

    val step = next - begin
    return Args(
        filename = args[0],
        precision = precision,
        function = function,
        range = object : DoubleIterator() {

            var current = begin

            override fun hasNext() = current <= end

            override fun nextDouble(): Double {
                val ret = current

                current += step
                return ret
            }
        }
    )
}

private fun initFunctions(precision: Double): Map<String, MathFunction> {
    val cos = cos(precision)
    val sec = sec(cos)
    val sin = sin(cos)
    val csc = csc(sin)
    val cot = cot(sin, cos)
    val ln = ln(precision)
    val task = task(sec, cot, csc, log(ln))

    return mapOf(
        "cos" to cos,
        "sec" to sec,
        "sin" to sin,
        "csc" to csc,
        "cot" to cot,
        "ln" to ln,
        "task" to task,
    )
}

fun main(args: Array<String>) {
    val (filename, range, precision, function) = parseArgs(args) ?: run {
        System.err.println("Usage: <filename> <begin> <begin + step> <end> [precision: 1e-4] [function: task]")
        exitProcess(-1)
    }

    val functions = initFunctions(precision)
    val f = functions[function] ?: run {
        System.err.println("Unknown function. Allowed only: ${functions.keys.joinToString(", ")}")
        exitProcess(-2)
    }

    try {
        Files.newBufferedWriter(
            Paths.get(filename),
            StandardOpenOption.CREATE,
            StandardOpenOption.WRITE
        )
    } catch (e: IOException) {
        System.err.printf("Cannot open file: %s\n", e)
        exitProcess(-3)
    }.use { writer ->
        val writeCsv = writeCsv(writer)

        range.asSequence()
            .map { x -> listOf(x, f(x)).map(Double::toString) }
            .forEach(writeCsv)
    }
}
