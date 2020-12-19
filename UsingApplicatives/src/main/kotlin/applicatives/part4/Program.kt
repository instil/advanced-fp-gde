package applicatives.part4

import arrow.core.*
import arrow.core.extensions.semigroup
import arrow.core.extensions.validated.applicative.applicative
import arrow.core.extensions.validated.applicative.map

fun queryUser(question: String, pattern: Regex): Validated<String, String> {
    println(question)
    val answer = readLine() ?: ""
    if(pattern.matches(answer)) {
        return Valid(answer)
    }
    return Invalid("$answer does not match $pattern")
}

fun main() {
    val regex = "[A-Z a-z]+".toRegex()
    val name = queryUser("What's your name?", regex)
    val location = queryUser("Where do you live?", regex)

    val applicative = Validated.applicative(String.semigroup())
    val data = applicative.tupledN(name, location)
    val result = data.map(String.semigroup()) { "Hello ${it.a} from ${it.b}" }

    result.fold({ println("Sorry: $it") }, ::println)
}
