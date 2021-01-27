package kleisli.common

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

fun addProperties(vararg items: String) {
    items.reduce { last, current ->
        System.setProperty(last, current)
        current
    }
}

fun printProperties() {
    System.getProperties()
        .entries
        .map { (key, value) ->
            val paddedKey = key.toString().padStart(20)
            "$paddedKey | $value"
        }
        .forEach(::println)
}
