package state.part1.dsl

import state.part1.model.Project
import state.part1.model.Repo
import java.net.URI

@SpaceEntityMarker
class DslRepo(val location: URI) {
    fun toRepo() = Repo(location)
}

@SpaceEntityMarker
class DslProject(val name: String, val key: String) {
    val content = mutableListOf<DslRepo>()

    fun repo(location: URI, action: DslRepo.() -> Unit) =
        DslRepo(location).apply(action).also { content.add(it) }

    fun toProject() = Project(name, key)
}

@SpaceEntityMarker
class DslProjects {
    val content = mutableListOf<DslProject>()

    fun project(name: String, key: String, action: DslProject.() -> Unit) =
        DslProject(name, key).apply(action).also { content.add(it) }
}