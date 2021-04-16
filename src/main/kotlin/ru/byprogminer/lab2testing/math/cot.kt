package ru.byprogminer.lab2testing.math


fun cot(sin: MathFunction, cos: MathFunction): MathFunction = { x ->
    cos(x) / sin(x)
}
