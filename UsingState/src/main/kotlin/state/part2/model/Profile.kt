package state.part2.model

import arrow.optics.optics

@optics data class Profile(val forename: String,
                   val surname: String,
                   val email: String) {

    companion object {}
}
