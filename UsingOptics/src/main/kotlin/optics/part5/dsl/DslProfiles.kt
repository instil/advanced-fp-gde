package optics.part5.dsl

import optics.part5.model.Profile

@SpaceEntityMarker
class DslProfile {
    lateinit var forename: String
    lateinit var surname: String
    lateinit var email: String

    fun toProfile() = Profile(forename, surname, email)
}

@SpaceEntityMarker
class DslProfiles {
    val content = mutableListOf<DslProfile>()

    fun profile(action: DslProfile.() -> Unit) = DslProfile().apply(action).also { content.add(it) }
}
