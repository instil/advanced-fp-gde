package state.part1.model

import arrow.core.ListK
import arrow.optics.optics

@optics data class Blog(val title: String,
                val location: String,
                val content: ListK<String> = ListK.empty()) {

    companion object {}
}
