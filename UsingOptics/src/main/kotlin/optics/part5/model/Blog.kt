package optics.part5.model

import arrow.optics.optics

@optics data class Blog(val title: String,
                val location: String,
                val content: List<String> = emptyList()) {

    companion object {}
    override fun toString() = foldOverChildren("\t\t$title\n\t\t\t\t$location", content, "\t\t\t\t")
}
