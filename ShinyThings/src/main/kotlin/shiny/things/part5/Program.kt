package shiny.things.part5

import arrow.core.*
import arrow.core.extensions.either.semigroup.maybeCombine
import arrow.core.extensions.semigroup

fun main() {
    val func = { num: Int -> if(num % 2 == 0) Right(num) else Left(num) }
    val data = (1..6).map(func)

    val result = data.map { either ->
        either.maybeCombine(Int.semigroup(), Int.semigroup(), Right(100))
    }
    println(result)
}
