package optics.part0

import java.net.URI

@SpaceEntityMarker
class Repo(private val location: URI) {
    override fun toString() = "\t$location"
}

@SpaceEntityMarker
class Project(private val name: String, val key: String) {
    private val repos = mutableListOf<Repo>()

    fun repo(location: URI, action: Repo.() -> Unit) = Repo(location).apply(action).also { repos.add(it) }

    override fun toString() = foldOverChildren("Project '$name' ($key) with repos:", repos)
}

@SpaceEntityMarker
class Projects {
    private val projects = mutableListOf<Project>()

    fun project(name: String, key: String, action: Project.() -> Unit) =
        Project(name, key).apply(action).also { projects.add(it) }

    override fun toString() = foldOverChildren("Current projects:", projects)
}
