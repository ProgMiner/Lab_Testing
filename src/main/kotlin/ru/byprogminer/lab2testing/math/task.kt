package ru.byprogminer.lab2testing.math

import kotlin.math.PI
import kotlin.math.absoluteValue
import kotlin.math.pow


fun task(
    sec: MathFunction,
    cot: MathFunction,
    csc: MathFunction,
    log: BasedMathFunction,
): MathFunction = { x ->
    if (x <= 0) {
        // Positive infinity points
        if (((x / (PI / 2) - 3) / 4).let { (it.toInt() - it).absoluteValue < 1e-10 }) {
            Double.POSITIVE_INFINITY
        } else {
            val secX = sec(x)

            (secX / cot(x)).pow(2).pow(3) - csc(x) - secX
        }
    } else {
        val log3 = log(x, 3)
        val log5 = log(x, 5)

        (log5 / log5 * log3).pow(3).pow(3) / (log5 / log5 + log5 + log3)
    }
}
