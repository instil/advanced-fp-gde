package optics.part1.model

data class Project(val name: String,
                   val key: String,
                   val repos: List<Repo> = emptyList()) {

    override fun toString() = foldOverChildren("\t\tProject '$name' ($key) with repos:", repos)
}
