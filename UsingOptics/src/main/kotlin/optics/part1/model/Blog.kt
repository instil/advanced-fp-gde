package optics.part1.model

data class Blog(val title: String,
                val location: String,
                val content: List<String> = emptyList()) {

    override fun toString() = foldOverChildren("\t\t$title\n\t\t\t\t$location", content, "\t\t\t\t")
}
