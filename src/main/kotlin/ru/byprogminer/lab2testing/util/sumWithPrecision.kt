package ru.byprogminer.lab2testing.util

import kotlin.math.abs


fun Sequence<Double>.sumWithPrecision(precision: Double): Double = this
    .scan(.0) { acc, y -> acc + y }.windowed(2).map { ys -> abs(ys[1] - ys[0]) to ys[1] }
    .dropWhile { (d, _) -> d > precision }.first().second
