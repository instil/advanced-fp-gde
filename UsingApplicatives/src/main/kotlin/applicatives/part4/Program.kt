package applicatives.part4

import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.either.applicative.map

fun queryUser(question: String, pattern: Regex): Either<String, String> {
    println(question)
    val answer = readLine() ?: ""
    if(pattern.matches(answer)) {
        return Right(answer)
    }
    return Left("$answer does not match $pattern")
}

fun main() {
    val regex = "[A-Z a-z]+".toRegex()

    val applicative = Either.applicative<String>()
    val result = applicative.mapN(
        queryUser("What's your name?", regex),
        queryUser("Where do you live?", regex)
    ) { (name, location) -> "Hello $name from $location" }

    result.fix().fold({ println("Sorry: $it") }, ::println)
}
