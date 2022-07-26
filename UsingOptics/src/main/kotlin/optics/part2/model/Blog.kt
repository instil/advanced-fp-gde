package optics.part2.model

import arrow.core.ListK
import arrow.optics.optics

@optics data class Blog(val title: String,
                val location: String,
                val content: ListK<String> = ListK.empty()) {

    companion object {}
    override fun toString() = foldOverChildren("\t\t$title\n\t\t\t\t$location", content, "\t\t\t\t")
}
