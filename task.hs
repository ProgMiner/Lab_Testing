module Task (sec, cot, csc, task) where


sec = (/) 1 . cos
csc = (/) 1 . sin

cot x = cos x / sin x


task :: Double -> Double
task x | x <= 0 = let secX = sec x in (((secX / cot x) ** 2) ** 3) - csc x - sec x
task x | x  > 0 = let
    log3 = log x / log 3
    log5 = log x / log 5
  in (((log5 / log5 * log3) ** 3) ** 3) / (log5 / log5 + log5 + log3)
