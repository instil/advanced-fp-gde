package optics.part0

@SpaceEntityMarker
class Profile {
    lateinit var forename: String
    lateinit var surname: String
    lateinit var email: String

    override fun toString() = "$forename $surname at $email"
}

@SpaceEntityMarker
class Profiles {
    private val profiles = mutableListOf<Profile>()

    fun profile(action: Profile.() -> Unit) = Profile().apply(action).also { profiles.add(it) }

    override fun toString() = foldOverChildren("Current profiles:", profiles)
}
