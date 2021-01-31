package optics.part2

import arrow.core.ListK
import arrow.core.k
import arrow.optics.dsl.every
import arrow.optics.dsl.index
import arrow.optics.extensions.listk.each.each
import arrow.optics.extensions.listk.index.index
import optics.part2.dsl.*
import optics.part2.model.*
import optics.part2.model.Instance
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

fun createInstance(): Instance {
    var instance = Instance(dsl.title)

    with(dsl.projects) {
        content.forEach { dslProject ->
            var project = dslProject.toProject()

            dslProject.content.forEach { dslRepo ->
                val repo = dslRepo.toRepo()
                project = project.copy(repos = project.repos.plus(repo).k())
            }

            instance = instance.copy(projects = instance.projects.plus(project).k())
        }
    }

    with(dsl.profiles) {
        content.forEach { dslProfile ->
            val profile = dslProfile.toProfile()

            instance = instance.copy(profiles = instance.profiles.plus(profile).k())
        }
    }

    with(dsl.blogs) {
        content.forEach { dslBlog ->
            var blog = dslBlog.toBlog()
            dslBlog.content.forEach { item ->
                blog = blog.copy(content = blog.content.plus(item).k())
            }

            instance = instance.copy(blogs = instance.blogs.plus(blog).k())
        }
    }

    return instance
}
