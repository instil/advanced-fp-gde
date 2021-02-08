package optics.part6.dsl

@SpaceEntityMarker
class DslBlog(val title: String, val location: String) {
    val content = mutableListOf<String>()

    operator fun String.unaryPlus() = content.add(this)
}

@SpaceEntityMarker
class DslBlogs {
    val content = mutableListOf<DslBlog>()

    fun blog(title: String, location: String, action: DslBlog.() -> Unit) =
        DslBlog(title, location).apply(action).also { content.add(it) }
}
