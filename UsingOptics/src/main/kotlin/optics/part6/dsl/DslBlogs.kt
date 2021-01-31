package optics.part6.dsl

import optics.part6.model.Blog

@SpaceEntityMarker
class DslBlog(val title: String, val location: String) {
    val content = mutableListOf<String>()

    operator fun String.unaryPlus() = content.add(this)

    fun toBlog() = Blog(title, location)
}

@SpaceEntityMarker
class DslBlogs {
    val content = mutableListOf<DslBlog>()

    fun blog(title: String, location: String, action: DslBlog.() -> Unit) =
        DslBlog(title, location).apply(action).also { content.add(it) }
}
