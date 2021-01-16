package kleisli.part2

import arrow.core.*
import arrow.core.extensions.either.monad.monad
import arrow.mtl.Kleisli
import arrow.mtl.fix
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

fun main() {
    addAtreidesLineage()
    demoKleisli()

    breakLineage()
    demoKleisli()
}

private fun demoKleisli() {
    val either = Either.monad<String>()
    val kleisli = Kleisli { key: String ->
        propertyViaJVM(key)
    }

    val result = kleisli
        .andThen(either, kleisli)
        .andThen(either, kleisli)
        .andThen(either, kleisli)
        .andThen(either, kleisli)
        .fix()
        .run("Vladimir")

    println(result)
}
