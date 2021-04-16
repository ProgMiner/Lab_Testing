package ru.byprogminer.lab2testing.math

import io.mockk.impl.annotations.MockK
import io.mockk.junit5.MockKExtension
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.extension.ExtendWith
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.CsvFileSource
import ru.byprogminer.lab2testing.util.mockCsv
import ru.byprogminer.lab2testing.util.testPoint

@ExtendWith(MockKExtension::class)
class CosTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val DELTA = 1e-4
    }

    private val real = cos(PRECISION)

    @MockK
    private lateinit var mock: MathFunction

    @BeforeEach
    fun initMock() = mock.mockCsv("cos.csv")

    @ParameterizedTest(name = "Test cos({0} rad) = {1}")
    @CsvFileSource(resources = ["cos.csv"])
    fun test(x: Double, y: Double) =
        testPoint(mock(x), x, PRECISION, DELTA, f = real)
}
