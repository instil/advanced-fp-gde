package shiny.things.part3

import arrow.core.*

fun main() {
    val func = { num: Int -> if(num % 2 == 0) Right(num) else Left(num) }
    val data = (1..6).map(func)

    val result = data.map { either ->
        either.bifoldLeft(10, { a, b -> a + b}, {a,b -> a * b})
    }
    println(result)
}
