package optics.part3.model

import arrow.optics.optics

@optics data class Project(val name: String,
                   val key: String,
                   val repos: List<Repo> = emptyList()) {
    companion object {}

    override fun toString() = foldOverChildren("\t\tProject '$name' ($key) with repos:", repos)
}
