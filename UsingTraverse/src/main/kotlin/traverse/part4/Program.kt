package traverse.part4;

import arrow.core.*
import arrow.core.computations.validated
import arrow.core.extensions.list.traverse.traverse
import arrow.core.extensions.semigroup
import arrow.core.extensions.validated.applicative.applicative
import java.nio.file.Files
import java.nio.file.NoSuchFileException
import java.nio.file.Paths
import java.util.stream.Collectors.toList

fun propertyViaJVM(key: String): Validated<String, String> {
    val result = System.getProperty(key)
    return if (result != null) {
        Valid(result)
    } else {
        Invalid("No JVM property: $key")
    }
}

fun propertyViaFile(path: String, key: String): Validated<String, String> = try {
    val propertyValue = { str: String -> str.substring(str.indexOf("=") + 1) }
    val lines = Files.lines(Paths.get("input", path))
    lines
        .filter { it.startsWith("$key=") }
        .findFirst()
        .map(propertyValue)
        .map { Valid(it) as Validated<String, String> }
        .orElse(Invalid("No File property: $key"))
} catch (ex: NoSuchFileException) {
    Invalid("No properties file: $path")
}

fun readAllLines(path: String): Validated<String, List<String>> = try {
    Valid(
        Files.lines(Paths.get("input", path))
            .collect(toList())
    )
} catch (ex: NoSuchFileException) {
    Invalid("No file called $path")
}

suspend fun demoTraverse(data: List<String>) {
    val findViaJVM = { input: List<String> -> input.traverse(Validated.applicative(String.semigroup()), ::propertyViaJVM) }

    val findViaFile = { input: List<String> ->
        val file = "part3.properties"
        input.traverse(Validated.applicative(String.semigroup())) {
            propertyViaFile(file, it)
        }
    }

    val readEverything = { paths: List<String> -> paths.traverse(Validated.applicative(String.semigroup()), ::readAllLines).fix() }

    val result = validated<String, List<String>> {
        val a = !findViaJVM(data)
        val b = !findViaFile(a.fix())
        val c = !readEverything(b.fix())
        c.fix().flatten()
    }

    result.fold(
        { error -> println("The process failed!\n\t$error") },
        { lines ->
            println("The process was successful:")
            lines.map { "\t$it" }
                .forEach(::println)
        }
    )
}

suspend fun main() {
    System.setProperty("cagney", "cagney.lacy")
    System.setProperty("starsky", "starsky.hutch")
    System.setProperty("hart", "hart.hart")

    demoTraverse(listOf("cagney", "starsky", "hart"))
}
