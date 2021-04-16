package ru.byprogminer.lab2testing.util

import java.util.*


private data class CsvParser(
    val current: String,
    val result: List<String>,
    val quote: Int,
)

private fun parseLine(line: String): List<String>? {
    val (current, result, quotes) = line.fold(CsvParser("", emptyList(), 0)) { parser, c ->
        when (parser.quote) {
            0 -> when (c) {
                '"' -> parser.copy(quote = 1)
                ',' -> parser.copy(current = "", result = parser.result + parser.current)
                else -> parser.copy(current = parser.current + c)
            }

            1 -> if (c == '"') {
                // When quote in quotes
                parser.copy(quote = 2)
            } else {
                parser.copy(current = parser.current + c)
            }

            // When after quote in quotes arrived:
            //   - another one quote: just print quote
            //   - otherwise: close quotes
            2 -> parser.copy(
                current = parser.current + c,
                quote = if (c == '"') 1 else 0
            )

            else -> throw IllegalStateException()
        }
    }

    return if (quotes in arrayOf(0, 2)) result + current else null
}

fun readCsv(scanner: Scanner) =
    generateSequence { try { scanner.nextLine() } catch (e: Exception) { null } }
        .mapNotNull(::parseLine).asIterable()
