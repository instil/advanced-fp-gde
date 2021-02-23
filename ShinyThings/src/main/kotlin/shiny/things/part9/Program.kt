package shiny.things.part9

fun main() {
    val data1 = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90)

    val result = data1.takeLastWhile { it > 50 }
    println(result)
}
