package optics.part1.model

data class Instance(val title: String,
                    val projects:List<Project> = emptyList(),
                    val profiles: List<Profile> = emptyList(),
                    var blogs: List<Blog> = emptyList()) {

    override fun toString() = """Space Instance - '$title'
        ${foldOverChildren("Projects:", projects)}
        ${foldOverChildren("Profiles:", profiles)}
        ${foldOverChildren("Blogs:", blogs)}
        """.trimIndent()
}
