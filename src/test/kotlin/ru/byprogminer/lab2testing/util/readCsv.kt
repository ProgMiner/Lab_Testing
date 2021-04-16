package ru.byprogminer.lab2testing.util

import java.util.*


private data class CsvParser(
    val current: String,
    val result: List<String>,
    val quote: Quote,
) {

    enum class Quote {

        NORMAL, QUOTE, Q_QUOTE
    }
}

private fun parseLine(line: String): List<String>? {
    val (current, result, quotes) = line.fold(CsvParser("", emptyList(), CsvParser.Quote.NORMAL)) { parser, c ->
        when (parser.quote) {
            CsvParser.Quote.NORMAL -> when (c) {
                '"' -> parser.copy(quote = CsvParser.Quote.QUOTE)
                ',' -> parser.copy(current = "", result = parser.result + parser.current)
                else -> parser.copy(current = parser.current + c)
            }

            CsvParser.Quote.QUOTE -> if (c == '"') {
                // When quote in quotes
                parser.copy(quote = CsvParser.Quote.Q_QUOTE)
            } else {
                parser.copy(current = parser.current + c)
            }

            // When after quote in quotes arrived:
            //   - another one quote: just print quote
            //   - otherwise: close quotes
            CsvParser.Quote.Q_QUOTE -> parser.copy(
                current = parser.current + c,
                quote = if (c == '"') CsvParser.Quote.QUOTE else CsvParser.Quote.NORMAL
            )
        }
    }

    return if (quotes in arrayOf(CsvParser.Quote.NORMAL, CsvParser.Quote.Q_QUOTE)) result + current else null
}

fun readCsv(scanner: Scanner) =
    generateSequence { try { scanner.nextLine() } catch (e: Exception) { null } }
        .mapNotNull(::parseLine).asIterable()
