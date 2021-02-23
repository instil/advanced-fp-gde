package shiny.things.part0

import arrow.core.*

fun main() {
    val func = { num: Int -> if(num % 2 == 0) Right(num) else Left(num) }
    val data = (1..6).map(func)
    println(data)
}
