package ru.byprogminer.lab2testing.util

import java.io.IOException
import java.io.Writer


private val badChars = arrayOf(' ', ',', '"')

private fun prepareValue(value: String): String = value.replace("\"", "\"\"").let { ret ->
    when {
        value.any { it in badChars } -> "\"$ret\""
        else -> ret
    }
}

@Throws(IOException::class)
fun writeCsv(writer: Writer) = { row: List<String> ->
    writer.append(row.joinToString(",", transform = ::prepareValue))
        .append('\n').flush()
}
