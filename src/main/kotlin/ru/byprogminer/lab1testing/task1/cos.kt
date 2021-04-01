package ru.byprogminer.lab1testing.task1

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.floor


private const val PI2 = 2 * PI
private const val TAYLOR_TERMS = 26

private fun powMinusOne(p: Int) = 1 - (p % 2) * 2

private fun cosProd(x: Double, n: Int) = (1..(2 * n))
    .map { i -> x / i }.reduce { acc, v -> acc * v }

private fun doCos(x: Double) = 1 + (1 until TAYLOR_TERMS)
    .map { n -> powMinusOne(n) * cosProd(x, n) }
    .sum()

fun cos(x: Double): Double = x.let(::abs)
    .let { it - floor(it / PI2) * PI2 }
    .let(::doCos)
