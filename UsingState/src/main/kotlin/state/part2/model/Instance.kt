package state.part2.model

import arrow.core.ListK
import arrow.optics.optics

@optics data class Instance(val title: String,
                            val projects: ListK<Project> = ListK.empty(),
                            val profiles: ListK<Profile> = ListK.empty(),
                            val blogs: ListK<Blog> = ListK.empty()) {

    companion object {}
}
