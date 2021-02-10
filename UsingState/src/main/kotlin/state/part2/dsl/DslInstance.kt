package state.part2.dsl

fun spaceInstance(title: String = "An Instil Delivery",
                  action: DslInstance.() -> Unit) = DslInstance(title).apply(action)

@SpaceEntityMarker
class DslInstance(val title: String) {
    lateinit var projects: DslProjects
    lateinit var profiles: DslProfiles
    lateinit var blogs: DslBlogs

    fun profiles(action: DslProfiles.() -> Unit) = DslProfiles().apply(action).also { this.profiles = it }
    fun projects(action: DslProjects.() -> Unit) = DslProjects().apply(action).also { this.projects = it }
    fun blogs(action: DslBlogs.() -> Unit) = DslBlogs().apply(action).also { this.blogs = it }
}
