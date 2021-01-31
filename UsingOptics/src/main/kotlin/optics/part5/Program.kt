package optics.part5

import arrow.core.ListK
import arrow.core.k
import arrow.core.toT
import arrow.optics.Lens
import arrow.optics.dsl.at
import arrow.optics.dsl.every
import arrow.optics.dsl.index
import arrow.optics.extensions.list.cons.cons
import arrow.optics.extensions.listk.each.each
import arrow.optics.extensions.listk.index.index
import optics.part5.dsl.*
import optics.part5.model.*
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

fun <T, U> insert(container: U, item: T, lens: LensToList<T, U>): U {
    return lens.modify(container) { item.cons(it).k() }
}

fun main() {
    val instance = createInstance()
    println(instance)

    showSimple(instance)
    showIndexing(instance)
    showEvery(instance)
}

private fun showSimple(instance: Instance) {
    val newInstance = Instance.title.modify(instance) { "$it - as taught by Instil" }
    println(newInstance)
}

private fun showIndexing(instance: Instance) {
    val newInstance = Instance
        .projects.index(ListK.index(), 1)
        .repos.index(ListK.index(), 0)
        .location.modify(instance) { URI("http://nowhere.com") }

    println(newInstance)
}

private fun showEvery(instance: Instance) {
    val newInstance = with(Instance.profiles.every(ListK.each())) {
        forename.modify(instance, String::toUpperCase)
    }
    println(newInstance)
}

fun createInstance(): Instance  = with(dsl) {
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
        insert(result,blog,Instance.blogs)
    }

    return instance
}
