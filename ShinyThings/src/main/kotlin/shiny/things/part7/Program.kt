package shiny.things.part7

fun main() {
    val data1 = listOf(1, 3, 5, 7, 9)
    val data2 = listOf(2, 4, 6, 8, 10)

    val data3 = data1.zip(data2)
    println(data3)
}
