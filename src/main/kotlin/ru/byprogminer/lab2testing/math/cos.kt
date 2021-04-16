package ru.byprogminer.lab2testing.math

import ru.byprogminer.lab2testing.util.powMinusOne
import ru.byprogminer.lab2testing.util.sumWithPrecision
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.absoluteValue
import kotlin.math.floor


private const val PI2 = 2 * PI

private fun cosProd(x: Double, n: Int) = (1..(2 * n))
    .map { i -> x / i }.fold(1.0) { acc, v -> acc * v }

private fun doCos(precision: Double) = { x: Double -> (0..Int.MAX_VALUE).asSequence()
    .map { n -> powMinusOne(n) * cosProd(x, n) }.sumWithPrecision(precision) }

fun cos(precision: Double): MathFunction = { x ->
    x.let(::abs).let { it - floor(it / PI2) * PI2 }.let {
        when {
            (it - PI / 2).absoluteValue < precision -> .0
            (it - 3 * PI / 2).absoluteValue < precision -> .0
            else -> it.let(doCos(precision))
        }
    }
}
