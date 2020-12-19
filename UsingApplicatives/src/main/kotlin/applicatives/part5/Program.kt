package applicatives.part5

import arrow.core.*
import arrow.core.extensions.semigroup
import arrow.core.extensions.validated.applicative.applicative
import arrow.core.extensions.validated.applicative.map
import arrow.typeclasses.Semigroup

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

    val semigroup = object : Semigroup<String> {
        override fun String.combine(b: String): String {
            return "$this , $b"
        }

    }
    val applicative = Validated.applicative(semigroup)

    val data = applicative.tupledN(name, location)
    val result = data.map(semigroup) { "Hello ${it.a} from ${it.b}" }

    result.fold({ println("Sorry: $it") }, ::println)
}
