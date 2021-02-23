package shiny.things.part6

import arrow.core.extensions.list.apply.ap

fun main() {
    val data1 = listOf(1, 2, 3, 4)
    val data2 = listOf { num: Int -> num * 10 }
    val data3 = listOf (
        { num: Int -> num * 100 },
        { num: Int -> num * 1000 },
    )

    val result1 = data1.ap(data2)
    println(result1)

    val result2 = data1.ap(data3)
    println(result2)
}
