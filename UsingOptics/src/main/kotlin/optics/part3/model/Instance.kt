package optics.part3.model

import arrow.optics.optics

@optics data class Instance(val title: String,
                    val projects: List<Project> = emptyList(),
                    val profiles: List<Profile> = emptyList(),
                    val blogs: List<Blog> = emptyList()) {

    companion object {}

    override fun toString() = """Space Instance - '$title'
        ${foldOverChildren("Projects:", projects)}
        ${foldOverChildren("Profiles:", profiles)}
        ${foldOverChildren("Blogs:", blogs)}
        """.trimIndent()
}
