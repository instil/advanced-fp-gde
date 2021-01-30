package optics.part0

fun spaceInstance(title: String = "An Instil Delivery",
                  action: SpaceInstance.() -> Unit) = SpaceInstance(title).apply(action)

@SpaceEntityMarker
class SpaceInstance(private val title: String) {
    private lateinit var projects: Projects
    private lateinit var profiles: Profiles
    private lateinit var blogs: Blogs

    override fun toString() = "$title\n$profiles\n$projects\n$blogs\n"

    fun profiles(action: Profiles.() -> Unit) = Profiles().apply(action).also { this.profiles = it }
    fun projects(action: Projects.() -> Unit) = Projects().apply(action).also { this.projects = it }
    fun blogs(action: Blogs.() -> Unit) = Blogs().apply(action).also { this.blogs = it }
}
