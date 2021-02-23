package shiny.things.part2

import arrow.core.*

fun main() {
    val func = { num: Int -> if(num % 2 == 0) Right(num) else Left(num) }
    val data = (1..6).map(func)

    val result = data.map { either ->
        either.fold({ it * 10}, { it * 100 })
    }
    println(result)
}
