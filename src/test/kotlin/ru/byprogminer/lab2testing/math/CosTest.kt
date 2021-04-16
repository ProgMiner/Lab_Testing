package ru.byprogminer.lab2testing.math

import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import ru.byprogminer.lab2testing.util.testPoint

@ExtendWith(MockKExtension::class)
class CosTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val DELTA = 1e-4
    }

    private val real = cos(PRECISION)

    @ParameterizedTest(name = "Test cos({0} rad) = {1}")
    @CsvFileSource(resources = ["cos.csv"])
    fun testUnit(x: Double, y: Double) =
        testPoint(y, x, PRECISION, DELTA, f = real)
}
