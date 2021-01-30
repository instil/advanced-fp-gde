package optics.part1.model

data class Profile(val forename: String,
              val surname: String,
              val email: String) {

    override fun toString() = "\t\t$forename $surname at $email"
}
