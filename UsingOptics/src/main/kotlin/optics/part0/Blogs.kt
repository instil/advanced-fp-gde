package optics.part0

@SpaceEntityMarker
class Blog(private val title: String, private val location: String) {
    private val additionalContent = mutableListOf<String>()

    operator fun String.unaryPlus() = additionalContent.add(this)

    override fun toString() = foldOverChildren("$title\n\t\t$location", additionalContent, "\t\t")
}

@SpaceEntityMarker
class Blogs {
    private val blogs = mutableListOf<Blog>()

    fun blog(title: String, location: String, action: Blog.() -> Unit) =
        Blog(title, location).apply(action).also { blogs.add(it) }

    override fun toString() = foldOverChildren("Current blogs:", blogs)

}
