package traverse.part2

import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.list.traverse.traverse
import java.nio.file.Files
import java.nio.file.Paths
import java.nio.file.NoSuchFileException

fun propertyViaFile(path: String, name: String): Either<String, String> = try {
    val propertyValue = { str: String -> str.substring(str.indexOf("=") + 1)}
    val lines = Files.lines(Paths.get("input",path))
    lines
        .filter { it.startsWith(name) }
        .findFirst()
        .map (propertyValue)
        .map { Right(it) as Either<String, String> }
        .orElse(Left("No property called $name"))
} catch(ex: NoSuchFileException) {
    Left("No file called $path")
}

fun demoTraverse(fileName: String, input: List<String>) {
    val failure = { error: String -> println(error) }

    val success = { results: List<String> ->
        println("Results are:")
        results.map { s -> "\t$s" }
            .forEach(::println)
    }

    val readInFile = { name: String -> propertyViaFile(fileName, name) }

    val result = input.traverse(Either.applicative(), readInFile)

    result.fix()
        .fold(failure) { success(it.fix()) }
}

fun main() {
    val correctName = "part2.properties"
    val incorrectName = "false.properties"

    demoTraverse(correctName, listOf("foo", "bar", "zed"))
    demoTraverse(correctName, listOf("false", "bar", "zed"))
    demoTraverse(correctName, listOf("foo", "false", "zed"))
    demoTraverse(correctName ,listOf("foo", "bar", "false"))
    demoTraverse(incorrectName ,listOf("foo", "bar", "false"))
}
