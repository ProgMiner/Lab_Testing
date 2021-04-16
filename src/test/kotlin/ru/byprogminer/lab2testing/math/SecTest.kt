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
class SecTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val DELTA = 1e-4
    }

    @MockK
    private lateinit var cosMock: MathFunction

    @BeforeEach
    fun initCosMock() = cosMock.mockCsv("cos.csv")

    @ParameterizedTest(name = "Test sec({0} rad) = {1}")
    @CsvFileSource(resources = ["sec.csv"])
    fun testUnit(x: Double, y: Double) =
        assertEquals(y, sec(cosMock)(x), PRECISION)

    @ParameterizedTest(name = "Test sec({0} rad) = {1}")
    @CsvFileSource(resources = ["sec.csv"])
    fun testFull(x: Double, y: Double) =
        testPoint(y, x, PRECISION, DELTA, sec(cos(PRECISION)))
}
