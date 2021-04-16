package ru.byprogminer.lab2testing.util

import java.io.IOException
import java.io.Writer


private fun prepareValue(value: String): String {
    val ret = value.replace("\"", "\"\"")

    if (sequenceOf(' ', ',', '"').any(value::contains)) {
        return "\"$ret\""
    }

    return ret
}

@Throws(IOException::class)
fun writeCsv(writer: Writer) = { row: List<String> ->
    writer.append(row.joinToString(",", transform = ::prepareValue))
        .append('\n').flush()
}
