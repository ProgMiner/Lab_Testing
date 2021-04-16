package ru.byprogminer.lab2testing.math


fun csc(sin: MathFunction): MathFunction = { x ->
    1 / sin(x)
}
