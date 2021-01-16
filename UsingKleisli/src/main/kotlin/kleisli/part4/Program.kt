package kleisli.part4

import arrow.core.computations.either
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

fun breakLineage() {
    System.setProperty("Jessica", "Alia")
}

suspend fun main() {
    addAtreidesLineage()
    demoMonadicComposition()

    breakLineage()
    demoMonadicComposition()
}

private suspend fun demoMonadicComposition() {

    val result = either<String, String> {
        val a = !propertyViaJVM("Vladimir")
        val b = !propertyViaJVM(a)
        val c = !propertyViaJVM(b)
        val d = !propertyViaJVM(c)
        !propertyViaJVM(d)
    }

    println(result)
}
