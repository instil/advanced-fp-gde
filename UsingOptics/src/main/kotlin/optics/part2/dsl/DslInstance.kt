package optics.part2.dsl

import optics.part2.model.Instance

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

    fun toInstance() = Instance(title)
}
