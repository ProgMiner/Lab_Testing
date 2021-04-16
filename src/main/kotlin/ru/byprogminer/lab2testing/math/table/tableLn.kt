package ru.byprogminer.lab2testing.math.table

import ru.byprogminer.lab2testing.math.MathFunction
import java.lang.Double.NaN


private val values = arrayOf(
       NaN, 0.0000, 0.6931, 1.0986, 1.3863, 1.6094, 1.7918, 1.9459, 2.0794, 2.1972,
    2.3026, 2.3979, 2.4849, 2.5649, 2.6391, 2.7081, 2.7726, 2.8332, 2.8904, 2.9444,
    2.9957, 3.0445, 3.0910, 3.1355, 3.1781, 3.2189, 3.2581, 3.2958, 3.3322, 3.3673,
    3.4012, 3.4340, 3.4657, 3.4965, 3.5264, 3.5553, 3.5835, 3.6109, 3.6376, 3.6636,
    3.6889, 3.7136, 3.7377, 3.7612, 3.7842, 3.8067, 3.8286, 3.8501, 3.8712, 3.8918,
    3.9120, 3.9318, 3.9512, 3.9703, 3.9890, 4.0073, 4.0254, 4.0431, 4.0604, 4.0775,
    4.0943, 4.1109, 4.1271, 4.1431, 4.1589, 4.1744, 4.1897, 4.2047, 4.2195, 4.2341,
    4.2485, 4.2627, 4.2767, 4.2905, 4.3041, 4.3175, 4.3307, 4.3438, 4.3567, 4.3694,
    4.3820, 4.3944, 4.4067, 4.4188, 4.4308, 4.4427, 4.4543, 4.4659, 4.4773, 4.4886,
    4.4998, 4.5109, 4.5218, 4.5326, 4.5433, 4.5539, 4.5643, 4.5747, 4.5850, 4.5951,
    4.6052, 4.6151, 4.6250, 4.6347, 4.6444, 4.6540, 4.6634, 4.6728, 4.6821, 4.6913
)

val tableLn: MathFunction = { x -> values[x.toInt()] }