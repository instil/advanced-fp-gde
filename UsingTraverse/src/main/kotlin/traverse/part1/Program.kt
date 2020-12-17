package traverse.part1

import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.list.traverse.traverse

fun propertyViaJVM(key: String): Either<String, String> {
    val result = System.getProperty(key)
    return if (result != null) {
        Right(result)
    } else {
        Left("No JVM property: $key")
    }
}

fun demoTraverse(input: List<String>) {
    val failure = { error: String -> println(error) }
    val success = { results: List<String> ->
        println("Results are:")
        results.map { s -> "\t$s" }
            .forEach(::println)
    }

    val result = input.traverse(Either.applicative(), ::propertyViaJVM)
    result.fix()
        .fold(failure) { success(it.fix()) }
}

fun main() {
    System.setProperty("foo", "test1.txt")
    System.setProperty("bar", "test2.txt")
    System.setProperty("zed", "test3.txt")

    demoTraverse(listOf("foo", "bar", "zed"))
    demoTraverse(listOf("false", "bar", "zed"))
    demoTraverse(listOf("foo", "false", "zed"))
    demoTraverse(listOf("foo", "bar", "false"))
}
