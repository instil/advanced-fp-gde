package optics.part3.model

import arrow.optics.optics
import java.net.URI

@optics data class Repo(val location: URI) {
    companion object {}

    override fun toString() = "\t\t\t\t$location"
}
