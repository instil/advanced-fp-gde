package optics.part2.model

import arrow.core.ListK
import arrow.optics.optics

@optics data class Project(val name: String,
                   val key: String,
                   val repos: ListK<Repo> = ListK.empty()) {

    companion object {}
    override fun toString() = foldOverChildren("\t\tProject '$name' ($key) with repos:", repos)
}
