package state.part2.model

import arrow.optics.optics
import java.net.URI

@optics data class Repo(val location: URI) {
    companion object {}
}
