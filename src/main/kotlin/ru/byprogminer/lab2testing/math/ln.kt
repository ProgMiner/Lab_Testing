package ru.byprogminer.lab2testing.math

import ru.byprogminer.lab2testing.util.powMinusOne
import ru.byprogminer.lab2testing.util.sumWithPrecision
import kotlin.math.pow


private fun lnTail(x: Double, precision: Double): Double = -(1..Int.MAX_VALUE).asSequence()
    .map { k -> powMinusOne(k) * (x - 1).pow(k) / k }.sumWithPrecision(precision)

private fun lnRecursive(x: Double, precision: Double): Double =
    doLn(x - 1, precision) - (1..Int.MAX_VALUE).asSequence()
        .map { k -> powMinusOne(k) * (x - 1).pow(-k) / k }
        .sumWithPrecision(precision)

private fun doLn(x: Double, precision: Double): Double = when {
    x < 0 -> Double.NaN
    x == .0 -> Double.NEGATIVE_INFINITY
    x > 2 -> lnRecursive(x, precision)
    else -> lnTail(x, precision)
}

fun ln(precision: Double): MathFunction = { x ->
    doLn(x, precision)
}
