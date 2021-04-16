package ru.byprogminer.lab2testing.math


fun sec(cos: MathFunction): MathFunction = { x ->
    1 / cos(x)
}
