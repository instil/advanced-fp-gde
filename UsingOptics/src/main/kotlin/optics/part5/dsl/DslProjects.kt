package optics.part5.dsl

import java.net.URI

@SpaceEntityMarker
class DslRepo(val location: URI)

@SpaceEntityMarker
class DslProject(val name: String, val key: String) {
    val content = mutableListOf<DslRepo>()

    fun repo(location: URI, action: DslRepo.() -> Unit) =
        DslRepo(location).apply(action).also { content.add(it) }
}

@SpaceEntityMarker
class DslProjects {
    val content = mutableListOf<DslProject>()

    fun project(name: String, key: String, action: DslProject.() -> Unit) =
        DslProject(name, key).apply(action).also { content.add(it) }
}
