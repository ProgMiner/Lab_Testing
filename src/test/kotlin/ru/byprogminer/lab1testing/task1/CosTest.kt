package ru.byprogminer.lab1testing.task1

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNotEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource


class CosTest {

    companion object {

        private const val EPSILON = 1e-10
        private const val DELTA = 1e-4

        private const val PI = 3.1415926535897932384626433
        private const val PI2 = PI / 2

        private fun degToRad(x: Double) = x * PI / 180

        private fun testPoint(x: Double, y: Double, name: String? = x.toString()) = assertAll(
            { assertEquals(y, cos(x), EPSILON, "cos($name) ≠ $y") },
            { assertNotEquals(y, cos(x + DELTA), EPSILON, "cos($name + delta) = $y") },
            { assertNotEquals(y, cos(x - DELTA), EPSILON, "cos($name - delta) = $y") },
        )
    }

    @Test
    fun `Test cos zero values`() = testPoint(PI2, .0, "pi / 2")

    @Test
    fun `Test cos one values`() = assertAll(
            { testPoint(.0, 1.0) },
            { testPoint(PI, -1.0, "pi") },
    )

    @Test
    fun `Test cos minus not necessary`() = assertAll(
            { assertEquals(cos(.0), cos(-.0), EPSILON, "cos(x) ≠ cos(-x)") },
            { assertEquals(cos(1.0), cos(-1.0), EPSILON, "cos(x) ≠ cos(-x)") },
            { assertEquals(cos(1337.0), cos(-1337.0), EPSILON, "cos(x) ≠ cos(-x)") },
    )

    @Test
    fun `Test cos 2pi not necessary`() = assertAll(
            { assertEquals(cos(.0), cos(2 * PI), EPSILON, "cos(x) ≠ cos(x + 2pi)") },
            { assertEquals(cos(1.0), cos(1.0 + 2 * PI), EPSILON, "cos(x) ≠ cos(x + 2pi)") },
            { assertEquals(cos(1337.0), cos(1337.0 + 2 * PI), EPSILON, "cos(x) ≠ cos(x + 2pi)") },
            { assertEquals(cos(1.0), cos(1.0 + 228 * PI), EPSILON, "cos(x) ≠ cos(x + 2y * pi)") },
            { assertEquals(cos(1337.0), cos(1337.0 + 228 * PI), EPSILON, "cos(x) ≠ cos(x + 2y * pi)") },
            { assertEquals(cos(1337.0), cos(1337.0 - 228 * PI), EPSILON, "cos(x) ≠ cos(x - 2y * pi)") },
    )

    @ParameterizedTest(name = "Test cos({0} deg) = {2}")
    @CsvFileSource(resources = ["table_values.csv"], numLinesToSkip = 1)
    fun `Test cos table values`(x: Double, y: Double, name: String) =
            testPoint(degToRad(x), y, "cos($x deg) ≠ $name")

    @Test
    fun `Test extremal values`() = assertAll(
            { assertEquals(Double.NaN, cos(Double.POSITIVE_INFINITY), "cos(Inf) ≠ NaN") },
            { assertEquals(Double.NaN, cos(Double.NEGATIVE_INFINITY), "cos(-Inf) ≠ NaN") },
            { assertEquals(Double.NaN, cos(Double.NaN), "cos(NaN) ≠ NaN") },
    )
}
