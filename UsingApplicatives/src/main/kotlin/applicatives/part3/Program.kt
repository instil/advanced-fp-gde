package applicatives.part3

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
    val name = queryUser("What's your name?", regex)
    val location = queryUser("Where do you live?", regex)

    val applicative = Either.applicative<String>()
    val data = applicative.tupledN(name, location)
    val result = data.map { "Hello ${it.a} from ${it.b}" }

    result.fold({ println("Sorry: $it") }, ::println)
}
