package state.part1

import arrow.core.*
import arrow.core.extensions.id.monad.monad
import arrow.mtl.State
import arrow.mtl.extensions.fx
import arrow.optics.Lens
import arrow.optics.extensions.list.cons.cons
import state.part1.dsl.spaceInstance
import state.part1.model.*
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
    displayInstance(instance)
}

fun tab(num: Int) = "".padStart(num,'\t')

fun displayInstance(instance: Instance) {
    val result = State.fx<Instance, ForId, List<String>>(Id.monad()) {
        val title = !displayTitle(instance)
        val profiles = !displayProfiles(instance)
        val projects = !displayProjects(instance)
        val blogs = !displayBlogs(instance)

        listOf(title, profiles, projects, blogs).flatten()
    }.runA(Id.monad(), instance).fix()

    result.value().forEach(::println)
}

fun displayTitle(instance: Instance) = State<Instance, List<String>> {
    instance toT ListK.just(instance.title)
}

fun displayProjects(instance: Instance) = State<Instance, List<String>> {
    val output = instance.projects.flatMap { project ->
        val title = "${tab(2)}Project '${project.name}' (${project.key}) with repos:"
        val repos = project.repos.map { repo ->
            "${tab(4)}${repo.location}"
        }
        title.cons(repos).k()
    }
    instance toT "${tab(1)}Projects are:".cons(output)
}

fun displayProfiles(instance: Instance) = State<Instance, List<String>> {
    val output = instance.profiles.map {
        "${tab(2)}${it.forename} ${it.surname} at ${it.email}"
    }
    instance toT "${tab(1)}Profiles are:".cons(output)
}

fun displayBlogs(instance: Instance) = State<Instance, List<String>> {
    val output:List<String> = instance.blogs.flatMap {
        listOf("${tab(2)}${it.title}", "${tab(3)}${it.location}")
            .plus(it.content.map{ str -> "${tab(3)}$str" })
            .k()
    }
    instance toT "${tab(1)}Blogs are:".cons(output)
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
