package typeclasses.arrow

import arrow.core.*
import arrow.Kind2
import arrow.core.ForEither
import arrow.core.ForValidated

interface Competition<T> {
    fun <U, V> Kind2<T, U, V>.judgeFairly(): String
    fun <U, V> Kind2<T, U, V>.judgeUnfairly(): String
}

object Judgement  {
    fun forEither(): Competition<ForEither> = JudgeEither
    fun forValidated(): Competition<ForValidated> = JudgeValidated

    private object JudgeEither: Competition<ForEither> {
        override fun <U, V> Kind2<ForEither, U, V>.judgeFairly(): String {
            return this.fix().fold({"loser"}, {"winner"})
        }

        override fun <U, V> Kind2<ForEither, U, V>.judgeUnfairly(): String {
            return this.fix().fold({"winner"}, {"loser"})
        }
    }

    private object JudgeValidated: Competition<ForValidated> {
        override fun <U, V> Kind2<ForValidated, U, V>.judgeFairly(): String {
            return this.fix().fold({"loser"}, {"winner"})
        }

        override fun <U, V> Kind2<ForValidated, U, V>.judgeUnfairly(): String {
            return this.fix().fold({"winner"}, {"loser"})
        }
    }
}


fun main() {
    val tst1 = Right(123)
    val tst2 = Left(456)
    val tst3 = Valid(123)
    val tst4 = Invalid(456)

    Judgement.forEither().run {
        println(tst1.judgeFairly())
        println(tst1.judgeUnfairly())

        println(tst2.judgeFairly())
        println(tst2.judgeUnfairly())
    }

    Judgement.forValidated().run {
        println(tst3.judgeFairly())
        println(tst3.judgeUnfairly())

        println(tst4.judgeFairly())
        println(tst4.judgeUnfairly())
    }
}
