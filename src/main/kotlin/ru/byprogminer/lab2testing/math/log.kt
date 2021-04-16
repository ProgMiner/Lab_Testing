package ru.byprogminer.lab2testing.math


fun log(ln: MathFunction): BasedMathFunction = { x, base ->
    ln(x) / ln(base.toDouble())
}
