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
class TaskTest {

    companion object {

        private const val PRECISION = 1e-10
        private const val EPSILON = 1e-5
        private const val DELTA = 0.6
    }

    @MockK
    private lateinit var cosMock: MathFunction

    @MockK
    private lateinit var secMock: MathFunction

    @MockK
    private lateinit var sinMock: MathFunction

    @MockK
    private lateinit var cscMock: MathFunction

    @MockK
    private lateinit var cotMock: MathFunction

    @MockK
    private lateinit var lnMock: MathFunction

    @MockK
    private lateinit var logMock: BasedMathFunction

    @BeforeEach
    fun initCosMock() = cosMock.mockCsv("cos.csv")

    @BeforeEach
    fun initSecMock() = secMock.mockCsv("sec.csv")

    @BeforeEach
    fun initSinMock() = sinMock.mockCsv("sin.csv")

    @BeforeEach
    fun initCscMock() = cscMock.mockCsv("csc.csv")

    @BeforeEach
    fun initCotMock() = cotMock.mockCsv("cot.csv")

    @BeforeEach
    fun initLnMock() = lnMock.mockCsv("ln.csv")

    @BeforeEach
    fun initLogMock() = logMock.mockCsv("log.csv")

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testUnit(x: Double, y: Double) =
        assertEquals(y, task(secMock, cotMock, cscMock, logMock)(x), EPSILON)

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithCosMock(x: Double, y: Double) {
        val ln = ln(PRECISION)

        val sec = sec(cosMock)
        val sin = sin(cosMock)
        val csc = csc(sin)
        val cot = cot(sin, cosMock)
        val log = log(ln)

        assertEquals(y, task(sec, cot, csc, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithSecMock(x: Double, y: Double) {
        val cos = cos(PRECISION)
        val ln = ln(PRECISION)

        val sin = sin(cos)
        val csc = csc(sin)
        val cot = cot(sin, cos)
        val log = log(ln)

        assertEquals(y, task(secMock, cot, csc, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithSinMock(x: Double, y: Double) {
        val cos = cos(PRECISION)
        val ln = ln(PRECISION)

        val sec = sec(cos)
        val csc = csc(sinMock)
        val cot = cot(sinMock, cos)
        val log = log(ln)

        assertEquals(y, task(sec, cot, csc, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithCscMock(x: Double, y: Double) {
        val cos = cos(PRECISION)
        val ln = ln(PRECISION)

        val sec = sec(cos)
        val sin = sin(cos)
        val cot = cot(sin, cos)
        val log = log(ln)

        assertEquals(y, task(sec, cot, cscMock, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithCotMock(x: Double, y: Double) {
        val cos = cos(PRECISION)
        val ln = ln(PRECISION)

        val sec = sec(cos)
        val sin = sin(cos)
        val csc = csc(sin)
        val log = log(ln)

        assertEquals(y, task(sec, cotMock, csc, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithLnMock(x: Double, y: Double) {
        val cos = cos(PRECISION)

        val sec = sec(cos)
        val sin = sin(cos)
        val csc = csc(sin)
        val cot = cot(sin, cos)
        val log = log(lnMock)

        assertEquals(y, task(sec, cot, csc, log)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testWithLogMock(x: Double, y: Double) {
        val cos = cos(PRECISION)

        val sec = sec(cos)
        val sin = sin(cos)
        val csc = csc(sin)
        val cot = cot(sin, cos)

        assertEquals(y, task(sec, cot, csc, logMock)(x), EPSILON)
    }

    @ParameterizedTest(name = "Test task({0}) = {1}")
    @CsvFileSource(resources = ["task.csv"])
    fun testFull(x: Double, y: Double) {
        val cos = cos(PRECISION)
        val ln = ln(PRECISION)

        val sec = sec(cos)
        val sin = sin(cos)
        val csc = csc(sin)
        val cot = cot(sin, cos)
        val log = log(ln)

        testPoint(y, x, EPSILON, DELTA, task(sec, cot, csc, log))
    }
}
