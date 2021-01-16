package kleisli.part1

import arrow.core.*
import kleisli.common.addProperties
import kleisli.common.propertyViaJVM


fun addAtreidesLineage() = addProperties(
    "Vladimir",
    "Jessica",
    "Paul",
    "Ghanima",
    "Moneo",
    "Siona"
)

fun main() {
    addAtreidesLineage()

    val readProperty = { input: Either<String, String> ->
        if(input.isLeft()) {
            input
        } else {
            propertyViaJVM(input.getOrElse { "" })
        }
    }

    val result1 = propertyViaJVM("Vladimir")
    val result2 = readProperty(result1)
    val result3 = readProperty(result2)
    val result4 = readProperty(result3)
    val result5 = readProperty(result4)

    println(result5)
}
