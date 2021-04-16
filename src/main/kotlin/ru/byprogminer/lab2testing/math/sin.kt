package ru.byprogminer.lab2testing.math

import kotlin.math.PI


fun sin(cos: MathFunction): MathFunction = { x ->
    cos(x - PI / 2)
}
