package hkt.part1

import arrow.core.*

object Competition {
    fun <T, U> judge(input: Either<T, U>) = if(input.isRight()) "winner" else "loser"
    fun <T, U> judge(input: Validated<T, U>) = if(input.isValid) "winner" else "loser"
}

fun main() {
    val tst1 = Right(123)
    val tst2 = Left(456)
    val tst3 = Valid(123)
    val tst4 = Invalid(456)

    println(Competition.judge(tst1))
    println(Competition.judge(tst2))
    println(Competition.judge(tst3))
    println(Competition.judge(tst4))
}
