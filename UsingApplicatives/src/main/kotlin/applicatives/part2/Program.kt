package applicatives.part2

import arrow.core.*

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

    val action = { name: String, location: String -> "Hello $name from $location" }
    val result = location.ap(name.map(action.curry()))

    result.fold({ println("Sorry: $it") }, ::println)
}
