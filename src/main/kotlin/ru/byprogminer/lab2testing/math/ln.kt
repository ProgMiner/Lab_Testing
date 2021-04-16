package ru.byprogminer.lab2testing.math

import ru.byprogminer.lab2testing.util.sumWithPrecision


// https://en.wikipedia.org/wiki/Logarithm#Calculation
fun ln(precision: Double): MathFunction = { x ->
    when {
        x < 0 -> Double.NaN
        x == .0 -> Double.NEGATIVE_INFINITY
        else -> {
            val z = (x - 1) / (x + 1)
            val sqr = z * z

            val kSeq = generateSequence(z) { it * sqr }
            val iSeq = generateSequence(1) { it + 2 }

            2 * kSeq.zip(iSeq) { k, i -> k / i }.sumWithPrecision(precision)
        }
    }
}
