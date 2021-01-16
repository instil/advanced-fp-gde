package kleisli.part3

import arrow.core.*
import arrow.core.extensions.either.applicative.applicative
import arrow.core.extensions.either.monad.monad
import arrow.mtl.Kleisli
import arrow.mtl.fix
import kleisli.common.addProperties
import kleisli.common.propertyViaJVM

fun addAtreidesLineage() = addProperties(
    "Vladimir Harkonnen",
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
    val eitherMonad = Either.monad<String>()
    val eitherApplicative = Either.applicative<String>()

    val kleisli = Kleisli { key: String ->
        propertyViaJVM(key)
    }

    val result = kleisli
        .local { name: String -> "$name Harkonnen" }
        .andThen(eitherMonad, kleisli)
        .andThen(eitherMonad, kleisli)
        .andThen(eitherMonad, kleisli)
        .andThen(eitherMonad, kleisli)
        .map(eitherApplicative) { name: String -> "$name Atreides" }
        .fix()
        .run("Vladimir")

    println(result)
}
