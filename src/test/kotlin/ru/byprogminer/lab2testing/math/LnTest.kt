package ru.byprogminer.lab2testing.math

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import ru.byprogminer.lab2testing.util.testPoint


@ExtendWith(MockKExtension::class)
class LnTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val DELTA = 1e-4
    }

    private val real = ln(PRECISION)

    @ParameterizedTest(name = "Test ln({0}) = {1}")
    @CsvFileSource(resources = ["ln.csv"])
    fun testUnit(x: Double, y: Double) = when {
        y.isFinite() -> testPoint(y, x, PRECISION, DELTA, f = real)
        else -> Assertions.assertEquals(y, real(x), PRECISION, "ln($x) ≠ $y")
    }
}
