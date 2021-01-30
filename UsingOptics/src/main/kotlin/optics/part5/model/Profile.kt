package optics.part5.model

import arrow.optics.optics

@optics data class Profile(val forename: String,
                   val surname: String,
                   val email: String) {

    companion object {}

    override fun toString() = "\t\t$forename $surname at $email"
}
