package traverse.part0

import arrow.core.Either
import arrow.core.Left
import arrow.core.Right

fun propertyViaJVM(key: String): Either<String, String> {
    val result = System.getProperty(key)
    return if (result != null) {
        Right(result)
    } else {
        Left("No JVM property: $key")
    }
}

fun main() {
    System.setProperty("foo", "test1.txt")
    System.setProperty("bar", "test2.txt")
    System.setProperty("zed", "test3.txt")

    val data1 = listOf("foo", "bar", "zed")
    val result1 = data1.map(::propertyViaJVM)
    println(result1)

    val data2 = listOf("foo", "false", "zed")
    val result2 = data2.map(::propertyViaJVM)
    println(result2)
}
