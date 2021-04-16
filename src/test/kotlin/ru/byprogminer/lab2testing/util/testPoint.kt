package ru.byprogminer.lab2testing.util

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.assertAll


fun testPoint(
    expected: Double,
    x: Double,
    precision: Double,
    delta: Double,
    f: (x: Double) -> Double
) = assertAll(
    { assertEquals(expected, f(x), precision, "f($x) ≠ $expected") },
    { assertNotEquals(expected, f(x + delta), precision, "f($x + delta) = $expected") },
    { assertNotEquals(expected, f(x - delta), precision, "f($x - delta) = $expected") },
)
