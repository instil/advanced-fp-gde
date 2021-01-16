package kleisli.part0

import kleisli.common.addProperties
import kleisli.common.printProperties

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
    printProperties()
}
