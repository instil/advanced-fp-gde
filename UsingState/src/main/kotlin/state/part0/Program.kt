package state.part0

import arrow.core.*
import arrow.optics.Lens
import arrow.optics.extensions.list.cons.cons
import state.part0.dsl.spaceInstance
import state.part0.model.*
import java.net.URI

val dsl = spaceInstance("Kotlin Programming for MegaCorp") {
    profiles {
        profile {
            forename = "Jane"
            surname = "Jones"
            email = "Jane.Jones@megacorp.com"
        }
        profile {
            forename = "Pete"
            surname = "Smith"
            email = "Pete.Smith@megacorp.com"
        }
    }
    projects {
        project("Course Examples", "PROJ101") {
            repo(URI("http://somewhere.com")) {}
        }
        project("Course Exercises", "PROJ202") {
            repo(URI("http://elsewhere.com")) {}
        }
    }
    blogs {
        blog("Welcome to the Course", "welcome.md") {
            +"Some additional client-specific content"
        }
        blog("Setting Up", "setup.md") {
            +"More client-specific content"
        }
    }
}

typealias LensToList<T, U> = Lens<U, ListK<T>>

fun main() {
    val instance = createInstance()
    println(instance)
}

fun createInstance(): Instance = with(dsl) {
    fun <T, U> insert(container: U, item: T, lens: LensToList<T, U>): U {
        return lens.modify(container) { item.cons(it).k() }
    }
    var instance = projects.content.fold(toInstance()) { result, dslProject ->
        val project = dslProject.content.fold(dslProject.toProject()) { result, dslRepo ->
            insert(result, dslRepo.toRepo(), Project.repos)
        }
        insert(result, project, Instance.projects)
    }

    instance = profiles.content.fold(instance) { result, dslProfile ->
        insert(result, dslProfile.toProfile(), Instance.profiles)
    }

    instance = blogs.content.fold(instance) { result, dslBlog ->
        val blog = dslBlog.content.fold(dslBlog.toBlog()) { result, item ->
            insert(result, item, Blog.content)
        }
        insert(result,blog, Instance.blogs)
    }

    return instance
}
