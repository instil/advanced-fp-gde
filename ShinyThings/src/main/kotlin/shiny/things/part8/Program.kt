package shiny.things.part8

import arrow.core.extensions.list.monadLogic.interleave
import arrow.core.extensions.list.zip.zipWith

fun main() {
    val data1 = listOf(1, 2, 3, 4, 5)
    val data2 = listOf(0.1, 0.2, 0.3, 0.4, 0.5)

    val result1 = data1.zipWith(data2) { num1, num2 -> "'$num1 and $num2'"}
    println(result1)

    val result2 = data1.interleave(data2)
    println(result2)
}
