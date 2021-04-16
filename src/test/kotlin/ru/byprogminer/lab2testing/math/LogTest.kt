package ru.byprogminer.lab2testing.math

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import ru.byprogminer.lab2testing.util.mockCsv
import ru.byprogminer.lab2testing.util.testPoint


@ExtendWith(MockKExtension::class)
class LogTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val DELTA = 1e-4
    }

    @MockK
    private lateinit var lnMock: MathFunction

    @BeforeEach
    fun initLnMock() = lnMock.mockCsv("ln.csv")

    @ParameterizedTest(name = "Test log({0}, {1}) = {2}")
    @CsvFileSource(resources = ["log.csv"])
    fun testUnit(x: Double, base: Double, y: Double) =
        assertEquals(y, log(lnMock)(x, base), PRECISION)

    @ParameterizedTest(name = "Test log({0}, {1}) = {2}")
    @CsvFileSource(resources = ["log.csv"])
    fun testFull(x: Double, base: Double, y: Double) {
        val log = log(ln(PRECISION))

        if (y.isFinite()) {
            testPoint(y, x, PRECISION, DELTA) { log(it, base) }
        } else {
            assertEquals(y, log(x, base), PRECISION, "log($x, $base) ≠ $y")
        }
    }
}
